package com.dahai.wanwu.mq.producer;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.dahai.wanwu.config.MqConfig;

@Configuration
public class RabbitConfig {
	@Resource
	private MqConfig mqConfig;
	
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses(mqConfig.getHost());
		connectionFactory.setUsername(mqConfig.getUsername());
		connectionFactory.setVirtualHost(mqConfig.getVirtualHost());
		connectionFactory.setPassword(mqConfig.getPassword());
		connectionFactory.setPublisherConfirms(mqConfig.getPublisherConfirms());
		return connectionFactory;
	}
	
	@Bean
	@Scope("prototype")
	public RabbitTemplate rabbitTemplate() {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
		rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
		return rabbitTemplate;
	}
	
	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
