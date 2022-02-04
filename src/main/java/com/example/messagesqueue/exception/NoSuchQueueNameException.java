package com.example.messagesqueue.exception;


public class NoSuchQueueNameException extends Exception { 

	private static final long serialVersionUID = 1L; // what is the need for serialise? best approach here to suppress?

	public NoSuchQueueNameException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}