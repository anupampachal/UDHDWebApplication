package com.scsinfinity.udhd.services.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

//@Service
public class MessagePublisher implements IMessagePublisher {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private ChannelTopic topic;

	public MessagePublisher() {
	}

	public MessagePublisher(final RedisTemplate<String, Object> redisTemplate, final ChannelTopic topic) {
		this.redisTemplate = redisTemplate;
		this.topic = topic;
	}

	public void publish(final String message) {
		redisTemplate.convertAndSend(topic.getTopic(), message);
	}
}