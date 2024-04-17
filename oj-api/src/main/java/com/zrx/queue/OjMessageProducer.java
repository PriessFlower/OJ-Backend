package com.zrx.queue;

import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OjMessageProducer {

	@Resource
	private RabbitTemplate rabbitTemplate;

	public void sendMessage(String exchange, String routingKey, String message) {
		rabbitTemplate.convertAndSend(exchange, routingKey, message);
	}

}