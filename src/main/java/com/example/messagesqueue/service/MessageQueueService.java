package com.example.messagesqueue.service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.messagesqueue.NoSuchQueueNameException;
import com.example.messagesqueue.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;

public class MessageQueueService {
	
	private static Logger logger = LogManager.getLogger(MessageQueueService.class);
	
	private static Map<String,ConcurrentLinkedQueue<Message>> queuesMap = new ConcurrentHashMap<String,ConcurrentLinkedQueue<Message>>();
	
	public static Message[] readMessages(String queueName, int size) throws NoSuchElementException, IndexOutOfBoundsException {
		logger.debug("Entering readMessages method with {}...", queueName);
		Message[] readMessages = new Message[size];
//		try { // acceptable approach ??
			if (!queuesMap.containsKey(queueName)) {
				logger.error("Requested Queue '{}' not found.", queueName);
				throw new NoSuchElementException();
			}
			if (queuesMap.get(queueName).size()<size) {
				logger.error("Read failed. Read size '{}' exceeds queue size '{}'.", size, queuesMap.get(queueName).size());
				throw new IndexOutOfBoundsException(size);
			}
			for(int i=0; i<size; ++i) {
				readMessages[i] = queuesMap.get(queueName).remove(); 
			}
//		}
//		catch (Exception e) {
//			logger.error("General exception occurred."); // define a custom exception for appn
//		}
		return readMessages;
	}
	
	public static String writeMessage(String queueName, Message message) throws NoSuchQueueNameException {
		logger.debug("Entering writeMessage method with {}...", queueName);
		try {
			if (queuesMap.containsKey(queueName)) {
				queuesMap.get(queueName).add(message); // write own exception?
			}
			else { 
				logger.error("Requested Queue '{}' not found.", queueName);
				throw new NoSuchElementException(); 
			}
		} catch (NoSuchElementException ex) {
			throw new NoSuchQueueNameException(String.format("Queue '{}' not found.", queueName), ex);
		}
		return "Write to queue successful.";
	}
	
	public static String createQueue(String queueName) throws QueueAlreadyExistsException{
		logger.debug("Entering createQueue method with {}...", queueName);
		if (queuesMap.containsKey(queueName)) {
			logger.error("Create failed. Queue already present.");
			throw new QueueAlreadyExistsException("");
			// return "Queue already created.";
			// replace with custom exception for key/queue already present
		}
		
		queuesMap.put(queueName,new ConcurrentLinkedQueue<Message>());
		return String.format("New message queue created with name %s.", queueName);
	}

}
