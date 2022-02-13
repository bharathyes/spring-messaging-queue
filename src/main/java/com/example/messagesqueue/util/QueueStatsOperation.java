package com.example.messagesqueue.util;

import com.example.messagesqueue.model.MessageQueue;

//@FunctionalInterface
public interface QueueStatsOperation {

    void writeSuccess(MessageQueue messageQueue, int size);

    void readSuccess(MessageQueue messageQueue, int size);

}
