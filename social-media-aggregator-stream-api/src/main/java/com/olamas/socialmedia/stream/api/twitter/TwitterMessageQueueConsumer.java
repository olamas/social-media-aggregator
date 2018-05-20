package com.olamas.socialmedia.stream.api.twitter;

import com.olamas.socialmedia.stream.api.queue.QueueConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.olamas.socialmedia.twitter.TwitterMessage;


@Component
public class TwitterMessageQueueConsumer extends QueueConsumer<TwitterMessage> {

    public static final String TWEET_QUEUE_TYPE = "tweet";

    @Autowired
    private TwitterMessageRepository twitterMessageRepository;

    public TwitterMessageQueueConsumer() {
        super(TWEET_QUEUE_TYPE);
    }

    @Override
    public void process(TwitterMessage tweet) {
        twitterMessageRepository.save(tweet);
    }

    @Override
    public void save() {

    }
}
