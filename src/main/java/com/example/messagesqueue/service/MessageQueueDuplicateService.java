package com.example.messagesqueue.service;

import java.util.List;

import com.example.messagesqueue.model.StatisticsType;
import org.springframework.stereotype.Service;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.model.MessageStatistics;

@Service
public class MessageQueueDuplicateService implements QueueService {

	@Override
	public Message[] readMessages(String queueName, int size)
			throws NoSuchQueueNameException, IndexOutOfBoundsException {
		return null;
	}

	@Override
	public String writeMessage(String queueName, List<Message> message) throws NoSuchQueueNameException {
		return null;
	}

	@Override
	public String createQueue(String queueName, StatisticsType statsType) throws QueueAlreadyExistsException {
		return null;
	}

	@Override
	public MessageStatistics getQueueStats(String queueName) throws NoSuchQueueNameException {
		return null;
	}

}
