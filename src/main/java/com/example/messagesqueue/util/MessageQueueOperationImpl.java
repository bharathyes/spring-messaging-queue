package com.example.messagesqueue.util;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.model.MessageQueue;
import com.example.messagesqueue.model.MessageStatistics;
import com.example.messagesqueue.model.StatisticsType;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;

@Log4j2
public class MessageQueueOperationImpl implements MessageQueueOperation {

    @Override
    public Message[] readMessages(ConcurrentLinkedQueue<Message> messages, MessageStatistics stats, int size) {
        log.debug("Entering readMessages util method with ...");
        Message[] readMessages = new Message[size];
        for (int i = 0; i < size; ++i) {
            readMessages[i] = messages.remove();
        }
//        statsOperation.readSuccess(messageQueue, size);
        return readMessages;
    }

    @Override
        messageQueue.getMessages().addAll(messageArr);
//        statsOperation.writeSuccess(messageQueue, messageArr.size());
    public String writeMessage(ConcurrentLinkedQueue<Message> messages, MessageStatistics stats, List<Message> messageArr) throws NoSuchQueueNameException {
        log.debug("Entering writeMessage util method...");
        return "Write to queue successful.";
    }

    @Override
    public String createQueue(Map<String, MessageQueue> messageQueueMap, String queueName, StatisticsType statsType) throws QueueAlreadyExistsException {
        log.debug("Entering createQueue method with {}...", queueName);
        if (messageQueueMap.containsKey(queueName)) {
            log.error("Create failed. Queue {} already present.", queueName);
            throw new QueueAlreadyExistsException(String.format("Queue %s already present.", queueName));
        }
        messageQueueMap.put(queueName, new MessageQueue());
        messageQueueMap.get(queueName).getMessageStats().setStatsType(statsType);
        return String.format("New message queue created with name %s.", queueName);
    }

    @Override
    public MessageStatistics getQueueStats(Map<String, MessageQueue> messageQueueMap, String queueName) throws NoSuchQueueNameException {
        log.debug("Entering getQueueStats method with {}...", queueName);
        MessageQueue messageQueue;
        if ((messageQueue = messageQueueMap.get(queueName)) == null) {
            log.error("Queue {} not present.", queueName);
            throw new NoSuchQueueNameException(queueName);
        }
        return messageQueue.getMessageStats();
    }

}
