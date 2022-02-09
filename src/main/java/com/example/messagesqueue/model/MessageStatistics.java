package com.example.messagesqueue.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MessageStatistics {

	private LocalDateTime creationTime = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
	
	private Integer messageCount = 0;
	
	private Integer readCount = 0;
	
	private Integer writeCount = 0;
	
	
	public void incrementReadCount() {
		++this.readCount;
	}
	
	public void incrementWriteCount() {
		++this.writeCount;
	}
	
}
