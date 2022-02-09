package com.example.messagesqueue.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.model.MessageQueue;

@Service
public class MessageQueueDuplicateService implements QueueService {

	@Override
	public Message[] readMessages(String queueName, int size)
			throws NoSuchQueueNameException, IndexOutOfBoundsException {
		return null;
	}

	@Override
	public String writeMessage(String queueName, ArrayList<Message> message) throws NoSuchQueueNameException {
		return null;
	}

	@Override
	public String createQueue(String queueName) throws QueueAlreadyExistsException {
		return null;
	}

	@Override
	public MessageQueue getQueueStats(String queueName) throws NoSuchQueueNameException {
		return null;
	}

}
