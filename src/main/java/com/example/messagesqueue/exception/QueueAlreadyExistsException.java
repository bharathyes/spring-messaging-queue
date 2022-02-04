package com.example.messagesqueue.exception;

@SuppressWarnings("serial")
public class QueueAlreadyExistsException extends Exception{

	public QueueAlreadyExistsException (String errorMessage) {
        super(errorMessage);
    }
}
