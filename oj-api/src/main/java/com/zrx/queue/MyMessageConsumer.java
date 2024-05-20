package com.zrx.queue;

import com.rabbitmq.client.Channel;
import com.zrx.execudeCode.JudgeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class MyMessageConsumer {

	// @Autowired
	// private JudgeService judgeService;

	// 指定程序监听的消息队列和确认机制
	@SneakyThrows
	@RabbitListener(queues = { "judge_queue" }, ackMode = "MANUAL")
	public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
		log.info("receiveMessage message = {}", message);
		long questionSubmitId = Long.parseLong(message);
		// judgeService.doJudge(questionSubmitId);
		channel.basicAck(deliveryTag, false);
		channel.basicNack(deliveryTag, false, false);
	}

}
