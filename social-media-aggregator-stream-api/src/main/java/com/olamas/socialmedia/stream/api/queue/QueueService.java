package com.olamas.socialmedia.stream.api.queue;

import com.leansoft.bigqueue.BigQueueImpl;
import com.leansoft.bigqueue.IBigQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class QueueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueService.class);

    private String queueServiceTweetsDir = "./queueservice/tweets";

    private ExecutorService serviceProducerConsumer;

    private IBigQueue bigQueue;

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private final String queueDir;

    private String type;

    public QueueService() {
        this.queueDir = queueServiceTweetsDir;
    }

    public boolean initQueue(String type) {
        boolean isQueueReady = false;
        this.type = type;
        try {
            if (bigQueue == null) {
                LOGGER.info("Starting to creating queue:" + type + "in "+queueDir);
                bigQueue = new BigQueueImpl(queueDir, type);
                LOGGER.info("Creating queue:" + type + " successfully..");
                startCleanMonitor();
            }
            isQueueReady = true;

        } catch (IOException e) {
            LOGGER.error("Error trying to create queues type: " + this.type, e);
        }
        return isQueueReady;
    }

    public <T> void startProducerConsumer(QueueProducer<T> producer, QueueConsumer<T> consumer) {
        serviceProducerConsumer = Executors.newFixedThreadPool(2);

        consumer.setDelay(30);
        consumer.setInitialDelay(20);
        consumer.setTotalItemCounts(10);
        consumer.setQueue(bigQueue);

        LOGGER.info("Starting consumer queue for type :" + this.type);
        producer.setQueue(bigQueue);
        producer.setTotalItemCounts(10);

        LOGGER.info("Starting producer queue for type :" + this.type);
        serviceProducerConsumer.execute(consumer);
        serviceProducerConsumer.execute(producer);
    }

    private void startCleanMonitor() {
        if (!executor.isShutdown()) {
            LOGGER.info("Starting monitor to clean objects from queue - type:" + this.type);
            executor.scheduleWithFixedDelay(new CleanQueuesMonitor(),5,
                    600, TimeUnit.SECONDS);
        }
    }

    public void shutdownQueueService() {
        try {
            if (serviceProducerConsumer != null) {
                serviceProducerConsumer.shutdown();
                serviceProducerConsumer.awaitTermination(10L, TimeUnit.SECONDS);
            }

            if (executor != null) {
                executor.shutdown();
            }
            if (bigQueue != null) {
                LOGGER.debug("gc de big queue for queue - type:" + this.type);
                bigQueue.gc();
                bigQueue.close();
            }

        } catch (Exception e) {
            LOGGER.error("Unexpected error trying to shutdown queue service. Cause", e);
        }

    }

    public String getType() {
        return type;
    }

    class CleanQueuesMonitor implements Runnable {
        @Override
        public void run() {
            try {
                if (bigQueue != null) {
                    LOGGER.debug("gc de big queue for queue - type:" + type);
                    bigQueue.gc();
                }
            } catch (Exception e) {
                LOGGER.error("There was an error cleaning queues", e);
            }
        }
    }

}
