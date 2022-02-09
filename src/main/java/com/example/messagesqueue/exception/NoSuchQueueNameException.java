package com.example.messagesqueue.exception;


public class NoSuchQueueNameException extends Exception { 

	private static final long serialVersionUID = 1L;

	public NoSuchQueueNameException(String errorMessage) {
        super(errorMessage);
    }
}