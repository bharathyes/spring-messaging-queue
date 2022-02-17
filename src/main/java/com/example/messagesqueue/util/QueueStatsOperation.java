package com.example.messagesqueue.util;

import com.example.messagesqueue.model.MessageStatistics;
import com.example.messagesqueue.model.StatisticsType;

public interface QueueStatsOperation {

    static void updateReadCount(MessageStatistics stats, int size) {
        if (stats.getStatsType() == StatisticsType.RECORD)
            stats.incrementReadCount(size);
        else
            stats.incrementReadCount(1);
        stats.updateMessageCount(-size);
    }

    static void updateWriteCount(MessageStatistics stats, int size){
        if (stats.getStatsType() == StatisticsType.RECORD)
            stats.incrementWriteCount(size);
        else
            stats.incrementWriteCount(1);
        stats.updateMessageCount(size);
    }
}


