package com.example.messagesqueue.service;

import java.util.List;

import com.example.messagesqueue.model.StatisticsType;
import com.example.messagesqueue.util.MessageQueueOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.model.MessageStatistics;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MessageQueueService implements QueueService {

    @Autowired
    private MessageQueueOperation messageOperation;

    @Override
    public Message[] readMessages(String queueName, int size) throws NoSuchQueueNameException, IndexOutOfBoundsException {
        log.debug("Entering readMessages method with {}...", queueName);
        return messageOperation.readMessages(queueName, size);
    }

    @Override
    public String writeMessage(String queueName, List<Message> messageArr) throws NoSuchQueueNameException {
        log.debug("Entering writeMessage method with {}...", queueName);
        return messageOperation.writeMessage(queueName, messageArr);
    }

    @Override
    public String createQueue(String queueName, StatisticsType statsType) throws QueueAlreadyExistsException {
        log.debug("Entering createQueue method with {}...", queueName);
        return messageOperation.createQueue(queueName, statsType);
    }

    @Override
    public MessageStatistics getQueueStats(String queueName) throws NoSuchQueueNameException {
        log.debug("Entering getQueueStats method with {}...", queueName);
        return messageOperation.getQueueStats(queueName);
    }

}
