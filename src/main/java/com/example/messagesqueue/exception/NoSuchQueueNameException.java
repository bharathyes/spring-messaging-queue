package com.example.messagesqueue.exception;


public class NoSuchQueueNameException extends Exception { 

	private static final long serialVersionUID = 1L; // why useful if static members aren't serialised? Why is Exception Serializable?

	public NoSuchQueueNameException(String errorMessage) {
        super(errorMessage);
    }
}