package com.example.messagesqueue.controller;

import com.example.messagesqueue.exception.NoSuchQueueNameException;
import com.example.messagesqueue.exception.QueueAlreadyExistsException;
import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.service.MessageQueueService;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController 
@RequestMapping(path="message")
public class MessageRestController {
	
	private static Logger logger = LogManager.getLogger(MessageRestController.class);
	
	@Autowired
	private MessageQueueService messageQueueService;
	
	@GetMapping(path="read/{queueName}")
	public Message[] getMessage(@RequestParam(value="size",defaultValue="1") int size, @PathVariable("queueName") String queueName) {
		logger.debug("Entering createQueue method...");	
		try {
			return messageQueueService.readMessages(queueName, size);
		 }
		catch (NoSuchElementException ex1) {
			logger.error(ex1);
	         throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Queue Not Found"); 
	    }
		catch (IndexOutOfBoundsException ex2) {
			logger.error(ex2);
			throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Queue Index Out of Bounds.");
		}
	}
	
	@PostMapping(path="write/{queueName}")
	public String setMessage(@PathVariable("queueName") String queueName, @RequestBody final Message message) {
		logger.debug("Entering createQueue method...");	
		try {
			return messageQueueService.writeMessage(queueName,message);
		 }
	    catch (NoSuchQueueNameException ex) {
	    	logger.error(ex);
	        throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Queue Not Found");
	    }
	}
	
	@PostMapping(path="create-queue",params= {"queueName"})
	public String createQueue(String queueName) {
		logger.debug("Entering createQueue method...");	
		try {
			return messageQueueService.createQueue(queueName);
		 } // use Response Entity
	    catch (QueueAlreadyExistsException  ex) {
	    	logger.error(ex);
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Queue Already Present");
	        
	    }
	}

}
