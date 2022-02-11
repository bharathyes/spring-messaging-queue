package com.example.messagesqueue.model;

import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter @NoArgsConstructor
public class MessageQueue {

	private ConcurrentLinkedQueue<Message> messages = new ConcurrentLinkedQueue<>();

	private MessageStatistics messageStats = new MessageStatistics();

}
