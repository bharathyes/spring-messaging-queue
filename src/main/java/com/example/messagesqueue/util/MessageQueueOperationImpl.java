package com.example.messagesqueue.util;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.model.MessageQueue;
import com.example.messagesqueue.model.MessageStatistics;
import com.example.messagesqueue.model.StatisticsType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Log4j2
@Component
public class MessageQueueOperationImpl implements MessageQueueOperation {

    private final Map<String, MessageQueue> messageQueueMap = new ConcurrentHashMap<>();

    @Autowired
    private QueueStatsOperation statsOperation;

    public void registerQueueListener(QueueStatsOperation statsOperation) {
        this.statsOperation = statsOperation;
    }


    @Override
    public Message[] readMessages(String queueName, int size) throws NoSuchQueueNameException, IndexOutOfBoundsException {
        log.debug("Entering readMessages method with {}...", queueName);
        Message[] readMessages = new Message[size];
        MessageQueue messageQueue;
        ConcurrentLinkedQueue<Message> messages;
        if ((messageQueue = messageQueueMap.get(queueName)) == null) {
            log.error("Queue {} not present.", queueName);
            throw new NoSuchQueueNameException(queueName);
        } else if ((messages = messageQueue.getMessages()) == null || messages.size() < size) {
            log.error("Read failed. Read size '{}' exceeds queue size.", size);
            throw new IndexOutOfBoundsException(size);
        }
        this.registerQueueListener(statsOperation);
        new Thread(() -> {
            for (int i = 0; i < size; ++i) {
                readMessages[i] = messages.remove();
            }
            statsOperation.readSuccess(messageQueue, size);
        }).start();
        return readMessages;
    }

    @Override
    public String writeMessage(String queueName, List<Message> messageArr) throws NoSuchQueueNameException {
        log.debug("Entering writeMessage method with {}...", queueName);
        MessageQueue messageQueue;
        if ((messageQueue = messageQueueMap.get(queueName)) == null) {
            log.error("Queue {} not present.", queueName);
            throw new NoSuchQueueNameException(queueName);
        }
        new Thread(() -> {
            messageQueue.getMessages().addAll(messageArr);
            statsOperation.writeSuccess(messageQueue, messageArr.size());
        }).start();
        return "Write to queue successful.";
    }

    @Override
    public String createQueue(String queueName, StatisticsType statsType) throws QueueAlreadyExistsException {
        log.debug("Entering createQueue method with {}...", queueName);
        if (messageQueueMap.containsKey(queueName)) {
            log.error("Create failed. Queue {} already present.", queueName);
            throw new QueueAlreadyExistsException(String.format("Queue %s already present.", queueName));
        }
        new Thread(() -> {
            messageQueueMap.put(queueName, new MessageQueue());
            messageQueueMap.get(queueName).setStatsType(statsType);
        }).start();
        return String.format("New message queue created with name %s.", queueName);
    }

    @Override
    public MessageStatistics getQueueStats(String queueName) throws NoSuchQueueNameException {
        log.debug("Entering getQueueStats method with {}...", queueName);
        MessageQueue messageQueue;
        if ((messageQueue = messageQueueMap.get(queueName)) == null) {
            log.error("Queue {} not present.", queueName);
            throw new NoSuchQueueNameException(queueName);
        }
        return messageQueue.getMessageStats();
    }

}
