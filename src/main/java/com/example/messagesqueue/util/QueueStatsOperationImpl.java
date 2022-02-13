package com.example.messagesqueue.util;

import com.example.messagesqueue.model.MessageQueue;
import com.example.messagesqueue.model.MessageStatistics;
import com.example.messagesqueue.model.StatisticsType;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class QueueStatsOperationImpl implements QueueStatsOperation {

    @Override
    public void readSuccess(MessageQueue messageQueue, int size) {
        log.debug("Successful read operation.");
        MessageStatistics mQueue = messageQueue.getMessageStats();
        mQueue.updateMessageCount(-size);
        if (messageQueue.getStatsType().equals(StatisticsType.RECORD)) {
            mQueue.incrementReadCount(size);
        } else {
            mQueue.incrementReadCount(1);
        }
    }

    @Override
    public void writeSuccess(MessageQueue messageQueue, int size) {
        log.debug("Successful write operation.");
        MessageStatistics mQueue = messageQueue.getMessageStats();
        mQueue.updateMessageCount(size);
        if (messageQueue.getStatsType().equals(StatisticsType.RECORD)) {
            messageQueue.getMessageStats().incrementWriteCount(size);
        } else {
            messageQueue.getMessageStats().incrementWriteCount(1);
        }
    }
}
