package com.example.messagesqueue.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;

@Service(value = "primary") // contains component. only to mark this holds business logic. can use
							// //@Component(value="primary") ?
public class MessageQueueService implements QueueService {

	private Logger logger = LogManager.getLogger(MessageQueueService.class);

	private Map<String, ConcurrentLinkedQueue<Message>> queuesMap = new ConcurrentHashMap<String, ConcurrentLinkedQueue<Message>>();

	@Override
	public Message[] readMessages(String queueName, int size)
			throws NoSuchQueueNameException, IndexOutOfBoundsException {
		logger.debug("Entering readMessages method with {}...", queueName);
		Message[] readMessages = new Message[size];
		ConcurrentLinkedQueue<Message> messageQueue = queuesMap.get(queueName);
		// contains vs get vs ExcHand performance?
		// contains and get very similar. Depends on efficiency of hashCode generated for key.
		if (messageQueue == null) {
			logger.error("Queue {} not present.", queueName);
			throw new NoSuchQueueNameException(queueName);
		} else if (messageQueue.size() < size) {
			logger.error("Read failed. Read size '{}' exceeds queue size.", size);
			throw new IndexOutOfBoundsException(size);
		}
		for (int i = 0; i < size; ++i) {
			readMessages[i] = messageQueue.remove();
		}
		return readMessages;
	}

	@Override
	public String writeMessage(String queueName, Message message) throws NoSuchQueueNameException {
		logger.debug("Entering writeMessage method with {}...", queueName);
		try {
			queuesMap.get(queueName).add(message);
		} catch (NullPointerException ex) {
			logger.error(ex);
			throw new NoSuchQueueNameException(String.format("Queue '%s' not found.", queueName));
		}
		return "Write to queue successful.";
	}

	@Override
	public String createQueue(String queueName) throws QueueAlreadyExistsException {
		logger.debug("Entering createQueue method with {}...", queueName);
		if (queuesMap.containsKey(queueName)) {
			logger.error("Create failed. Queue already present.");
			throw new QueueAlreadyExistsException(String.format("Queue %s already present.", queueName));
		}

		queuesMap.put(queueName, new ConcurrentLinkedQueue<Message>());
		return String.format("New message queue created with name %s.", queueName);
	}

}
