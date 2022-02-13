package com.example.messagesqueue.model;

import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter @NoArgsConstructor
public class MessageQueue {
	
	// add enum => at creation: type: 1) records - ds count 2) request - end point count
	// use callback read & write

	private StatisticsType statsType;

	private ConcurrentLinkedQueue<Message> messages = new ConcurrentLinkedQueue<>();

	private MessageStatistics messageStats = new MessageStatistics();

}
