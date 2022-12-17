package com.github.kkhanhluu.jmsdemo.sender;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kkhanhluu.jmsdemo.config.JmsConfig;
import com.github.kkhanhluu.jmsdemo.model.HelloWorldMessage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HelloWorldSender {
	private final JmsTemplate jmsTemplate;
	private final ObjectMapper objectMapper;

	// @Scheduled(fixedRate = 2000)
	// public void sendMessage() {
	// System.err.println("I'm sending a message");

	// HelloWorldMessage message =
	// HelloWorldMessage.builder().id(UUID.randomUUID()).message("My first message
	// to queue")
	// .build();
	// jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);

	// System.out.println("Message was sent");
	// }

	@Scheduled(fixedRate = 2000)
	public void sendReceiveMessage() throws JMSException {
		Message receivedMessage = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RECEIVE_QUEUE, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				try {
					HelloWorldMessage message = HelloWorldMessage.builder().id(UUID.randomUUID())
							.message("Hello")
							.build();
					Message helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
					helloMessage.setStringProperty("_type", "com.github.kkhanhluu.jmsdemo.model.HelloWorldMessage");
					System.out.println("Sending Hello");
					return helloMessage;
				} catch (JsonProcessingException e) {
					throw new JMSException("Boom");
				}
			}
		});

		System.out.println(receivedMessage.getBody(String.class));
	}
}
