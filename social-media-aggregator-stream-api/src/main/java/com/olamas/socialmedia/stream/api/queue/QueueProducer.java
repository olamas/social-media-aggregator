package com.olamas.socialmedia.stream.api.queue;

import com.leansoft.bigqueue.IBigQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class QueueProducer<T> extends Thread {

    private static final Logger log = LoggerFactory.getLogger(QueueProducer.class);

    private IBigQueue bigQueue;

    private List<QueueConsumer<T>> consumers;

    private Integer totalItemCounts;

    private int totalMaxDeadItemCounts;

    private final AtomicInteger producingItemCount = new AtomicInteger(0);

    private final AtomicInteger producingDeadItemCount = new AtomicInteger(0);

    private boolean stop = false;

    private Object lock = new Object();

    private final String type;

    public QueueProducer(String type) {
        this.type = type;
        consumers = new ArrayList<QueueConsumer<T>>();
    }

    public void addItem(T document) {
        try {
            byte[] item = this.getItemSerialized(document);
            bigQueue.enqueue(item);
            producingItemCount.incrementAndGet();
            log.debug("Adding item " + producingItemCount.get() + " type: " + this.type + " into the queue");
            if (getTotalItemCounts() != null && producingItemCount.get() >= getTotalItemCounts()) {
                this.notifyConsumers();
                producingItemCount.set(0);
            }
        } catch (IOException ex) {
            log.error("Error trying to serealize item type: " + this.type, ex);
        } finally {

        }
    }

    public void notifyConsumers() {
        for (QueueConsumer<T> consumer : consumers) {
            consumer.nofityQueueReady();
        }
    }

    public void setQueue(IBigQueue bigQueue) {
        this.bigQueue = bigQueue;
    }

    public void addConsumer(QueueConsumer<T> consumer) {
        this.consumers.add(consumer);
    }

    public void run() {
        try {
            while (!this.stop) {
                synchronized (lock) {
                    lock.wait();
                    notifyConsumers();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopProducer() {
        synchronized (lock) {
            lock.notify();
        }
        this.stop = true;
    }

    private byte[] getItemSerialized(T document) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] item = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(document);
            item = bos.toByteArray();

        } catch (IOException ex) {
            log.error("Error trying to serealize item:" + ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                    bos.close();
                }
            } catch (IOException ex) {
                log.error("Error closing stream trying to serealize", ex);
            }
        }
        return item;
    }

    public Integer getTotalItemCounts() {
        return totalItemCounts;
    }

    public void setTotalItemCounts(Integer totalItemCounts) {
        this.totalItemCounts = totalItemCounts;
    }

    public int getTotalMaxDeadItemCounts() {
        return totalMaxDeadItemCounts;
    }

    public void setTotalMaxDeadItemCounts(int totalMaxDeadItemCounts) {
        this.totalMaxDeadItemCounts = totalMaxDeadItemCounts;
    }
}
