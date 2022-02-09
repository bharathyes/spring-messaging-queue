package com.example.messagesqueue.exception;

public class QueueAlreadyExistsException extends Exception{
	
	private static final long serialVersionUID = 2L;

	public QueueAlreadyExistsException (String errorMessage) {
        super(errorMessage);
    }
}
