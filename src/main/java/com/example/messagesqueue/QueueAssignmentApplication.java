package com.example.messagesqueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class QueueAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueueAssignmentApplication.class, args);
	}

}
