package com.zrx.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class InitRabbitMq {

	public static void init() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			String EXCHANGE_NAME = "luojialong";
			channel.exchangeDeclare(EXCHANGE_NAME, "direct");
			String queueName = "judge_queue";
			channel.queueDeclare(queueName, true, false, false, null);
			channel.queueBind(queueName, EXCHANGE_NAME, "shenming");
		}
		catch (Exception e) {

		}
	}

}
