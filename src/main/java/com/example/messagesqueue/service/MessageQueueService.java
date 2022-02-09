package com.example.messagesqueue.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.model.MessageQueue;

@Service
public class MessageQueueService implements QueueService {

	private Logger logger = LogManager.getLogger(MessageQueueService.class);

	private Map<String, MessageQueue> messageQueueMap = new ConcurrentHashMap<String, MessageQueue>();

	@Override
	public Message[] readMessages(String queueName, int size)
			throws NoSuchQueueNameException, IndexOutOfBoundsException {
		logger.debug("Entering readMessages method with {}...", queueName);
		Message[] readMessages = new Message[size];
		MessageQueue messageQueue = new MessageQueue();
		ConcurrentLinkedQueue<Message> messages = new ConcurrentLinkedQueue<Message>();
		if ((messageQueue = messageQueueMap.get(queueName)) == null) {
			logger.error("Queue {} not present.", queueName);
			throw new NoSuchQueueNameException(queueName);
		} else if ((messages = messageQueue.getMessages()) == null || messages.size() < size) {
			logger.error("Read failed. Read size '{}' exceeds queue size.", size);
			throw new IndexOutOfBoundsException(size);
		}
		for (int i = 0; i < size; ++i) {
			readMessages[i] = messages.remove();
		}
		messageQueue.incrementReadCount();
		return readMessages;
	}

	@Override
	public String writeMessage(String queueName, ArrayList<Message> messageArr) throws NoSuchQueueNameException {
		logger.debug("Entering writeMessage method with {}...", queueName);
		MessageQueue messageQueue = new MessageQueue();
		if ((messageQueue = messageQueueMap.get(queueName)) == null) {
			logger.error("Queue {} not present.", queueName);
			throw new NoSuchQueueNameException(queueName);
		}
		messageQueue.getMessages().addAll(messageArr);
		messageQueue.incrementWriteCount();
		return "Write to queue successful.";
	}

	@Override
	public String createQueue(String queueName) throws QueueAlreadyExistsException {
		logger.debug("Entering createQueue method with {}...", queueName);
		if (messageQueueMap.containsKey(queueName)) {
			logger.error("Create failed. Queue already present.");
			throw new QueueAlreadyExistsException(String.format("Queue %s already present.", queueName));
		}
		messageQueueMap.put(queueName, new MessageQueue());
		return String.format("New message queue created with name %s.", queueName);
	}
	
	@Override
	public MessageQueue getQueueStats(String queueName) throws NoSuchQueueNameException {
		logger.debug("Entering getQueueStats method with {}...", queueName);
		MessageQueue messageQueue = new MessageQueue();
		if ((messageQueue = messageQueueMap.get(queueName)) == null) {
			logger.error("Queue {} not present.", queueName);
			throw new NoSuchQueueNameException(queueName);
		}
		return messageQueue.queueStats();
	}

}
