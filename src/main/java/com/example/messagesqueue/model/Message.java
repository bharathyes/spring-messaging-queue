package com.example.messagesqueue.model;


// try using lambok 
public class Message {

	private String content;
	
	public Message() {
		
	}
	
	public Message(String text) {
		this.content = text;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
