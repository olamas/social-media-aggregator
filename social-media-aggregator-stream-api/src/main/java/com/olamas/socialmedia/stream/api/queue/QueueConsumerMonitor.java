package com.olamas.socialmedia.stream.api.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueConsumerMonitor <T> implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueConsumerMonitor.class);

    private QueueConsumer<T> consumerToMonitor;

    public QueueConsumerMonitor(final QueueConsumer<T> consumer) {
        this.consumerToMonitor = consumer;
    }

    @Override
    public void run() {
        LOGGER.info("Monitor is notifing Consumer to read queue");
        this.consumerToMonitor.nofityQueueReady();
    }
}
