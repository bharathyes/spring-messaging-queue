package com.example.messagesqueue.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.exception.QueueStatisticsNotFoundException;
import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.model.MessageStatistics;
import com.example.messagesqueue.model.StatisticsType;

public interface QueueService {

	Message[] readMessages(String queueName, int size)
			throws NoSuchQueueNameException, IndexOutOfBoundsException, QueueStatisticsNotFoundException;

	String writeMessage(String queueName, List<Message> messageArr) throws NoSuchQueueNameException, QueueStatisticsNotFoundException;

	String createQueue(String queueName, StatisticsType statsType) throws QueueAlreadyExistsException;

	MessageStatistics getQueueStats(String queueName) throws NoSuchQueueNameException;

}
