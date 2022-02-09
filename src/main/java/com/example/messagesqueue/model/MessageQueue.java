package com.example.messagesqueue.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class MessageQueue {

	private LocalDateTime creationTime = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
	
	private ConcurrentLinkedQueue<Message> messages = new ConcurrentLinkedQueue<Message>();
	
	private Integer messageCount = 0;
	
	private Integer readCount = 0;
	
	private Integer writeCount = 0;
	
	public void incrementReadCount() {
		++this.readCount;
	}
	
	public void incrementWriteCount() {
		++this.writeCount;
	}
	
	public MessageQueue queueStats() {
		return new MessageQueue(this.creationTime, new ConcurrentLinkedQueue<Message>(), this.messages.size(), this.readCount, this.writeCount);
	}
}
