package com.example.messagesqueue.controller;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.model.MessageQueue;
import com.example.messagesqueue.service.QueueService;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CustomAppController
@RequestMapping(path = "message")
public class MessageRestController {

	private static Logger logger = LogManager.getLogger(MessageRestController.class);

	@Autowired
	private QueueService messageQueueService;

	@GetMapping(path = "read/{queueName}")
	public ResponseEntity<Message[]> getMessage(@RequestParam(value = "size", defaultValue = "1") int size,
			@PathVariable("queueName") String queueName) {
		logger.debug("Entering createQueue method...");
		try {
			return new ResponseEntity<Message[]>(messageQueueService.readMessages(queueName, size), HttpStatus.OK);
		} catch (NoSuchQueueNameException ex) {
			logger.error("NoSuchElementEx with queue '{}': {}", queueName, ex);
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
		} catch (IndexOutOfBoundsException ex) {
			logger.error("Queue Index Out of Bounds. {}", ex);
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
		}
	}

	@PostMapping(path = "write/{queueName}")
	public ResponseEntity<String> setMessage(@PathVariable("queueName") String queueName,
			@RequestBody final ArrayList<Message> messageArr) {
		logger.debug("Entering createQueue method...");
		try {
			return new ResponseEntity<String>(messageQueueService.writeMessage(queueName, messageArr), HttpStatus.OK);
		} catch (NoSuchQueueNameException ex) {
			logger.error("NoSuchQueueNameException. {}", ex);
			return new ResponseEntity<String>("Queue Not Found", HttpStatus.PRECONDITION_FAILED);
		}
	}

	@PostMapping(path = "create-queue", params = { "queueName" })
	public ResponseEntity<String> createQueue(String queueName) {
		logger.debug("Entering createQueue method...");
		try {
			return new ResponseEntity<String>(messageQueueService.createQueue(queueName), HttpStatus.OK);
		} catch (QueueAlreadyExistsException ex) {
			logger.error("Queue already present. {}", ex);
			return new ResponseEntity<String>("Queue Already Present", HttpStatus.BAD_REQUEST);

		}
	}
	
	@GetMapping(path = "queue-stats", params = { "queueName" })
	public ResponseEntity<MessageQueue> getQueueStats(String queueName) throws NoSuchQueueNameException {
		logger.debug("Entering getQueueStats method...");
		try {
			return new ResponseEntity<>(messageQueueService.getQueueStats(queueName), HttpStatus.OK);
		} catch (NoSuchQueueNameException ex) {
			logger.error("NoSuchQueueNameException. {}", ex);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		}
	}

}
