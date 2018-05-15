package com.olamas.socialmedia.stream.api.twitter;

import com.olamas.socialmedia.stream.api.queue.QueueProducer;
import com.olamas.socialmedia.twitter.TwitterMessage;
import org.springframework.stereotype.Component;

@Component
public class TwitterMessageQueueProducer extends QueueProducer<TwitterMessage> {

    public static final String TWEET_QUEUE_TYPE = "tweet";

    public TwitterMessageQueueProducer(){
        super(TWEET_QUEUE_TYPE);
    }
}
