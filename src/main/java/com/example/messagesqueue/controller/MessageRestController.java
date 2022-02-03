package com.example.messagesqueue.controller;

import com.example.messagesqueue.NoSuchQueueNameException;
import com.example.messagesqueue.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.service.MessageQueueService;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController // annotations to use objects 
@RequestMapping(path="message")
public class MessageRestController {
	
	private static Logger logger = LogManager.getLogger(MessageRestController.class);
	
	@GetMapping(path="read/{queueName}")
	public Message[] getMessage(@RequestParam(value="size",defaultValue="1") int size, @PathVariable("queueName") String queueName) {
		logger.debug("Entering createQueue method...");	
		try {
			return MessageQueueService.readMessages(queueName, size);
		 }
		catch (NoSuchElementException ex1) {
	         throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Queue Not Found", ex1); 
	    }
		catch (IndexOutOfBoundsException ex2) {
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Queue Index Out of Bounds.", ex2);
		} // best approach to handle specific exceptions? 
	}
	
	@PostMapping(path="write/{queueName}")
	public String setMessage(@PathVariable("queueName") String queueName, @RequestBody final Message message) {
		logger.debug("Entering createQueue method...");	
		try {
			return MessageQueueService.writeMessage(queueName,message);
		 }
	    catch (NoSuchQueueNameException ex) {
	         throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Queue Not Found", ex); // handling different types of exceptions??
	    }
	}
	
	@PostMapping(path="create-queue",params= {"queueName"})
	public String createQueue(String queueName) {
		logger.debug("Entering createQueue method...");	
		try {
			return MessageQueueService.createQueue(queueName);
		 }
	    catch (QueueAlreadyExistsException  ex) {
	         throw new ResponseStatusException(
	           HttpStatus.BAD_REQUEST, "Queue Already Present", ex);
	    }
	}

}
