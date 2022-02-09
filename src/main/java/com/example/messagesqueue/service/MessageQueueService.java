package com.example.messagesqueue.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Service;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.model.MessageQueue;
import com.example.messagesqueue.model.MessageStatistics;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MessageQueueService implements QueueService {


	private Map<String, MessageQueue> messageQueueMap = new ConcurrentHashMap<String, MessageQueue>();

	@Override
	public Message[] readMessages(String queueName, int size)
			throws NoSuchQueueNameException, IndexOutOfBoundsException {
		log.debug("Entering readMessages method with {}...", queueName);
		Message[] readMessages = new Message[size];
		MessageQueue messageQueue = new MessageQueue();
		ConcurrentLinkedQueue<Message> messages = new ConcurrentLinkedQueue<Message>();
		if ((messageQueue = messageQueueMap.get(queueName)) == null) {
			log.error("Queue {} not present.", queueName);
			throw new NoSuchQueueNameException(queueName);
		} else if ((messages = messageQueue.getMessages()) == null || messages.size() < size) { 	// move actual operations to message utils
			log.error("Read failed. Read size '{}' exceeds queue size.", size);
			throw new IndexOutOfBoundsException(size);
		}
		for (int i = 0; i < size; ++i) {
			readMessages[i] = messages.remove();
		}
		messageQueue.getMessageStats().incrementReadCount();
		return readMessages;
	}

	@Override
	public String writeMessage(String queueName, ArrayList<Message> messageArr) throws NoSuchQueueNameException {
		log.debug("Entering writeMessage method with {}...", queueName);
		MessageQueue messageQueue = new MessageQueue();
		if ((messageQueue = messageQueueMap.get(queueName)) == null) {
			log.error("Queue {} not present.", queueName);
			throw new NoSuchQueueNameException(queueName);
		}
		messageQueue.getMessages().addAll(messageArr);
		messageQueue.getMessageStats().incrementWriteCount();
		return "Write to queue successful.";
	}

	@Override
	public String createQueue(String queueName) throws QueueAlreadyExistsException {
		log.debug("Entering createQueue method with {}...", queueName);
		if (messageQueueMap.containsKey(queueName)) {
			log.error("Create failed. Queue {} already present.", queueName);
			throw new QueueAlreadyExistsException(String.format("Queue %s already present.", queueName));
		}
		messageQueueMap.put(queueName, new MessageQueue());
		return String.format("New message queue created with name %s.", queueName);
	}
	
	@Override
	public MessageStatistics getQueueStats(String queueName) throws NoSuchQueueNameException {
		log.debug("Entering getQueueStats method with {}...", queueName);
		MessageQueue messageQueue = new MessageQueue();
		if ((messageQueue = messageQueueMap.get(queueName)) == null) {
			log.error("Queue {} not present.", queueName);
			throw new NoSuchQueueNameException(queueName);
		}
		return messageQueue.getMessageStats();
	}

}
