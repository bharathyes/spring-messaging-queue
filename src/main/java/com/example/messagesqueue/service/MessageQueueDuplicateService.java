package com.example.messagesqueue.service;

import org.springframework.stereotype.Service;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;

@Service
public class MessageQueueDuplicateService implements QueueService {

	@Override	// needed for interface??
	public Message[] readMessages(String queueName, int size)
			throws NoSuchQueueNameException, IndexOutOfBoundsException {
		return null;
	}

	@Override
	public String writeMessage(String queueName, Message message) throws NoSuchQueueNameException {
		return null;
	}

	@Override
	public String createQueue(String queueName) throws QueueAlreadyExistsException {
		return null;
	}

}
