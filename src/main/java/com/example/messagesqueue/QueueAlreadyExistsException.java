package com.example.messagesqueue;

@SuppressWarnings("serial")
public class QueueAlreadyExistsException extends Exception{

	public QueueAlreadyExistsException (String errorMessage) {
        super(errorMessage);
    }
}
