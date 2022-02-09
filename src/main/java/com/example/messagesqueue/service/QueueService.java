package com.example.messagesqueue.service;

import java.util.ArrayList;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.model.MessageQueue;

public interface QueueService {

	public Message[] readMessages(String queueName, int size)
			throws NoSuchQueueNameException, IndexOutOfBoundsException;

	public String writeMessage(String queueName, ArrayList<Message> messageArr) throws NoSuchQueueNameException;

	public String createQueue(String queueName) throws QueueAlreadyExistsException;
	
	public MessageQueue getQueueStats(String queueName) throws NoSuchQueueNameException;

}
