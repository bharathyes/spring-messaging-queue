package com.example.messagesqueue.exception;

public class QueueStatisticsNotFoundException extends Exception {

    private static final long serialVersionUID = 3L;

    public QueueStatisticsNotFoundException (String errorMessage) {
            super(errorMessage);
        }

}
