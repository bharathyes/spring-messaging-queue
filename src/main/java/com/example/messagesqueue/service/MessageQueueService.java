package com.example.messagesqueue.service;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import com.example.messagesqueue.model.Message;

public class MessageQueueService {
	
	private static Queue<Message> messageQueue = new LinkedList<>();
	
	public static Message[] readMessages(int size) {
		Message[] readMessages = new Message[size];
		try {
			for(int i=0; i<size; ++i) {
				readMessages[i] = messageQueue.remove();
			}
		}
		catch (NoSuchElementException e) {
			System.out.println("No queue element to read.");
		}
		return readMessages;
	}
	
	public static Message writeMessage(Message message) {
		messageQueue.add(message);
		return message;
	}

}
