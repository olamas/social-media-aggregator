package com.olamas.socialmedia.stream.api.twitter;


import com.olamas.socialmedia.stream.api.queue.QueueConsumer;
import com.olamas.socialmedia.stream.api.queue.QueueProducer;
import com.olamas.socialmedia.stream.api.queue.QueueService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.utils.EnsurePath;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;


@Component
public class TwitterService extends SourceDetectorService implements StreamListener{

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterService.class);

    public static final String ZOOKEEPER_CONECTION_HOST = "localhost:2181";

    public static final String SOCIAL_USERS_BASE_NODE = "/social/users";

    @Autowired
    private Twitter twitter;

    private Stream stream;

    @Autowired
    private TwitterAsyncWorker twitterAsyncWorker;

    @Autowired
    private QueueService queueService;

    @Autowired
    private TwitterMessageQueueProducer queueProducer;

    @Autowired
    private TwitterMessageQueueConsumer queueConsumer;

    private HashMap<String,TwitterFilter> filters;

    /*
        zookeeper configuration
     */
    private HashMap<String, String> nodePaths;

    private CuratorFramework curator;

    private Watcher watcher;

    @Override
    public void run() {
        LOGGER.info(" Starting streaming - initializing queues - watchers...");
        this.filters = new HashMap<String, TwitterFilter>();
        initQueue();
        configZookeeperClient();
        LOGGER.info("Starting service reading tweets ......");
    }

    private void initQueue() {
        this.queueService.initQueue(TwitterMessageQueueProducer.TWEET_QUEUE_TYPE);
        queueProducer.addConsumer(queueConsumer);
        queueService.startProducerConsumer(queueProducer,queueConsumer);
    }

    private void configZookeeperClient(){
        initWatcher();
        curator = CuratorFrameworkFactory.newClient(ZOOKEEPER_CONECTION_HOST, 10000, 2000, new RetryOneTime(2000));
        curator.start();
        try {
            // Ensure the group node exists
            new EnsurePath(SOCIAL_USERS_BASE_NODE).ensure(curator.getZookeeperClient());
            // Read users filters
            List<String> userFilters = curator.getChildren().usingWatcher(watcher).forPath(SOCIAL_USERS_BASE_NODE);
            readFilterConfiguration(userFilters);
        }
        catch (Exception e){
            LOGGER.error("Unable to start reading nodes - ");
        }
    }

    private void initWatcher() {
        watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                try {

                    System.out.println("---------------------- Adding new filter from : " + event.getPath());
                    List<String> userFilters = curator.getChildren().usingWatcher(watcher).forPath(SOCIAL_USERS_BASE_NODE);
                    readFilterConfiguration(userFilters);
                    System.out.println("---------------------- Adding new filter : " + userFilters.size());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private void readFilterConfiguration(List<String> userFilters){
        List<StreamListener> listeners = new ArrayList<>();
        listeners.add(this);
        FilterStreamParameters filter = new FilterStreamParameters();
        for (String userFilter:userFilters) {
            try {
                String filterValue = new String(curator.getData().usingWatcher(watcher).forPath(SOCIAL_USERS_BASE_NODE+"/"+userFilter));
                System.out.println("Adding filter : " + filterValue);
                filter.track(filterValue);
                updateFilterList(userFilter,filterValue);
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(this.stream!=null)
            this.stream.close();

        this.stream = twitter.streamingOperations().filter(filter,listeners);
    }

    private void updateFilterList(String userFilter, String filterValue) {
        TwitterFilter twitterFilter = filters.get(userFilter);
        if (twitterFilter==null){
            twitterFilter = new TwitterFilter();
        }
        twitterFilter.setTopic(filterValue);
        filters.put(userFilter,twitterFilter);
    }

    private Object getResultOfFuture(Future future) throws Exception {
        if (future.isDone()) {
            return future.get();
        }
        LOGGER.info(" waiting for result ");
        Thread.sleep(1000);
        return getResultOfFuture(future);
    }

    @Override
    public void onTweet(Tweet tweet) {
        LOGGER.info(" returned Tweet is [" + tweet.getText() + "] ");
        TwitterMessage message = new TwitterMessage(tweet.getIdStr(),tweet.getText());
        message.setFromUser(tweet.getFromUser());
        this.queueProducer.addItem(message);
    }

    @Override
    public void onDelete(StreamDeleteEvent streamDeleteEvent) {

    }

    @Override
    public void onLimit(int i) {

    }

    @Override
    public void onWarning(StreamWarningEvent streamWarningEvent) {

    }

    @Override
    public void stop() {
        this.stream.close();
        System.out.println("Closing curator.............");
        curator.close();
    }
}
