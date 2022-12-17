package com.github.kkhanhluu.jmsdemo.listener;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.github.kkhanhluu.jmsdemo.config.JmsConfig;
import com.github.kkhanhluu.jmsdemo.model.HelloWorldMessage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HelloWorldMessageListener {

	private final JmsTemplate jmsTemplate;

	@JmsListener(destination = JmsConfig.MY_QUEUE)
	public void listen(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message) {
		System.out.println("I got a message");
		System.out.println(helloWorldMessage);
	}

	@JmsListener(destination = JmsConfig.MY_SEND_RECEIVE_QUEUE)
	public void listenForHello(@Payload HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers,
			Message message) throws JmsException, JMSException {
		HelloWorldMessage worldMessage = HelloWorldMessage.builder().id(UUID.randomUUID())
				.message("world")
				.build();
		jmsTemplate.convertAndSend(message.getJMSReplyTo(), worldMessage);
	}
}
