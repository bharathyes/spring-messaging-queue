package com.example.messagesqueue.controller;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.model.MessageQueue;
import com.example.messagesqueue.service.QueueService;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@CustomAppController
@RequestMapping(path = "message")
public class MessageRestController {

	@Autowired
	private QueueService messageQueueService;

	@GetMapping(path = "read/{queueName}")
	public ResponseEntity<Message[]> getMessage(@RequestParam(value = "size", defaultValue = "1") int size,
			@PathVariable("queueName") String queueName) {
		log.debug("Entering createQueue method...");
		try {
			return new ResponseEntity<Message[]>(messageQueueService.readMessages(queueName, size), HttpStatus.OK);
		} catch (NoSuchQueueNameException ex) {
			log.error("No such queue '{}'", queueName);
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
		} catch (IndexOutOfBoundsException ex) {
			log.error("Queue Index Out of Bounds.");
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
		}
	}

	@PostMapping(path = "write/{queueName}")
	public ResponseEntity<String> setMessage(@PathVariable("queueName") String queueName,
			@RequestBody final ArrayList<Message> messageArr) {
		log.debug("Entering createQueue method...");
		try {
			return new ResponseEntity<String>(messageQueueService.writeMessage(queueName, messageArr), HttpStatus.OK);
		} catch (NoSuchQueueNameException ex) {
			log.error("No such queue '{}'", queueName);
			return new ResponseEntity<String>("Queue Not Found", HttpStatus.PRECONDITION_FAILED);
		}
	}

	@PostMapping(path = "create-queue", params = { "queueName" })
	public ResponseEntity<String> createQueue(String queueName) {
		log.debug("Entering createQueue method...");
		try {
			return new ResponseEntity<String>(messageQueueService.createQueue(queueName), HttpStatus.OK);
		} catch (QueueAlreadyExistsException ex) {
			log.error("Queue already present.");
			return new ResponseEntity<String>("Queue Already Present", HttpStatus.BAD_REQUEST);

		}
	}
	
	@GetMapping(path = "queue-stats", params = { "queueName" })
	public ResponseEntity<MessageQueue> getQueueStats(String queueName) throws NoSuchQueueNameException {
		log.debug("Entering getQueueStats method...");
		try {
			return new ResponseEntity<>(messageQueueService.getQueueStats(queueName), HttpStatus.OK);
		} catch (NoSuchQueueNameException ex) {
			log.error("No such queue '{}'", queueName);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		}
	}

}
