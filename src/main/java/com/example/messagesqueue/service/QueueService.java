package com.example.messagesqueue.service;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;

public interface QueueService {

	public Message[] readMessages(String queueName, int size)
			throws NoSuchQueueNameException, IndexOutOfBoundsException;

	public String writeMessage(String queueName, Message message) throws NoSuchQueueNameException;

	public String createQueue(String queueName) throws QueueAlreadyExistsException;

}
