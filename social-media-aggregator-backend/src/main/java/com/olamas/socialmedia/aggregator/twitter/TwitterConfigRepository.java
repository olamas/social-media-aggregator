package com.olamas.socialmedia.aggregator.twitter;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.utils.EnsurePath;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TwitterConfigRepository {

    public static final String ZOOKEEPER_CONECTION_HOST = "localhost:2181";

    public static final String SOCIAL_USERS_BASE_NODE = "/social/users";

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterConfigRepository.class);
    public static final String NODE_ADDED_OK_MESSAGE = "NODE_ADDED_OK";

    private CuratorFramework curator;

    public TwitterFilter addNewFilter(TwitterFilter filter){
        this.initConfigServer();
        try {
            curator.blockUntilConnected();
            // Ensure the group node exists
            new EnsurePath(SOCIAL_USERS_BASE_NODE).ensure(curator.getZookeeperClient());
            // Read users filters
            byte[] bytesData = filter.getFilterText().getBytes();
            CuratorOp curatorOp = curator.transactionOp().create().withMode(CreateMode.PERSISTENT).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(SOCIAL_USERS_BASE_NODE+"/"+filter.getUserName(),bytesData);
            String result = curator.transaction().forOperations(curatorOp).get(0).toString();
            curator.close();
            filter.setResult(NODE_ADDED_OK_MESSAGE);
        }
        catch (Exception e){
            LOGGER.error("Unable to start reading nodes - ");
        }
        return filter;
    }

    private void initConfigServer(){
        curator = CuratorFrameworkFactory.newClient(ZOOKEEPER_CONECTION_HOST, 10000, 2000, new RetryOneTime(2000));
        curator.start();
    }

}
