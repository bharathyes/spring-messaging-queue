package com.example.messagesqueue.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.messagesqueue.model.Message;
import com.example.messagesqueue.service.MessageQueueService;

@RestController
@RequestMapping(path="message")
public class MessageRestController {
	
	@GetMapping(path="read")
	public Message[] getMessage(@RequestParam(value="size",defaultValue="1") int size) {
		return MessageQueueService.readMessages(size);
	}
	
	@PostMapping(path="write")
	public Message setMessage(@RequestBody final Message message) {
		return MessageQueueService.writeMessage(message);
	}

}
