package com.example.messagesqueue.util;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.model.MessageQueue;
import com.example.messagesqueue.model.MessageStatistics;
import com.example.messagesqueue.model.StatisticsType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;

public interface MessageQueueOperation {

    Message[] readMessages(ConcurrentLinkedQueue<Message> messages, MessageStatistics stats, int size) throws NoSuchQueueNameException, IndexOutOfBoundsException, ExecutionException, InterruptedException;

    String writeMessage(ConcurrentLinkedQueue<Message> messageQueueMap, MessageStatistics queueName, List<Message> messageArr) throws NoSuchQueueNameException;

    String createQueue(Map<String, MessageQueue> messageQueueMap, String queueName, StatisticsType statsType) throws QueueAlreadyExistsException;

    MessageStatistics getQueueStats(Map<String, MessageQueue> messageQueueMap, String queueName) throws NoSuchQueueNameException;

}