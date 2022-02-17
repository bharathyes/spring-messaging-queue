package com.example.messagesqueue.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;

import com.example.messagesqueue.exception.QueueStatisticsNotFoundException;
import com.example.messagesqueue.model.MessageQueue;
import com.example.messagesqueue.model.StatisticsType;
import com.example.messagesqueue.util.MessageQueueOperation;
import com.example.messagesqueue.util.MessageQueueOperationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.model.MessageStatistics;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MessageQueueService implements QueueService {

    private final MessageQueueOperation messageOperation = new MessageQueueOperationImpl();

    public Map<String, MessageQueue> messageQueueMap = new ConcurrentHashMap<>();

    @Override
    public Message[] readMessages(String queueName, int size) throws NoSuchQueueNameException, IndexOutOfBoundsException, QueueStatisticsNotFoundException {
        log.debug("Entering readMessages method with {}...", queueName);
        MessageQueue messageQueue;
        ConcurrentLinkedQueue<Message> messages;
        MessageStatistics stats;
        if ((messageQueue = messageQueueMap.get(queueName)) == null) {
            log.error("Queue {} not present.", queueName);
            throw new NoSuchQueueNameException(queueName);
        } else if ((stats= messageQueue.getMessageStats())==null) {
            log.error("Queue Statistics not found.");
            throw new QueueStatisticsNotFoundException(queueName);
        } else if ((messages = messageQueue.getMessages()) == null || messages.size() < size) {
            log.error("Read failed. Read size '{}' exceeds queue size.", size);
            throw new IndexOutOfBoundsException(size);
        }
        try {
            return messageOperation.readMessages(messages, stats, size);
        } catch (ExecutionException | InterruptedException ex) {
            return null;
        }
    }

    @Override
    public String writeMessage(String queueName, List<Message> messageArr) throws NoSuchQueueNameException, QueueStatisticsNotFoundException {
        log.debug("Entering writeMessage method with {}...", queueName);
        MessageQueue messageQueue;
        MessageStatistics stats;
        if ((messageQueue = messageQueueMap.get(queueName)) == null) {
            log.error("Queue {} not present.", queueName);
            throw new NoSuchQueueNameException(queueName);
        } else if ((stats= messageQueue.getMessageStats())==null) {
            log.error("Queue Statistics not found.");
            throw new QueueStatisticsNotFoundException(queueName);
        }
        ConcurrentLinkedQueue<Message>  messages = messageQueue.getMessages();
        return messageOperation.writeMessage(messages, stats, messageArr);
    }

    @Override
    public String createQueue(String queueName, StatisticsType statsType) throws QueueAlreadyExistsException {
        log.debug("Entering createQueue method with {}...", queueName);
        return messageOperation.createQueue(messageQueueMap, queueName, statsType);
    }

    @Override
    public MessageStatistics getQueueStats(String queueName) throws NoSuchQueueNameException {
        log.debug("Entering getQueueStats method with {}...", queueName);
        return messageOperation.getQueueStats(messageQueueMap, queueName);
    }

}
