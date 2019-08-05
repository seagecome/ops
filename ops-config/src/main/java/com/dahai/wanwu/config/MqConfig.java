package com.dahai.wanwu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:rabbitmq.properties")
public class MqConfig {
	@Value("${rabbitmq.host}")
	private String host;
	
	@Value("${rabbitmq.port}")
	private Integer port;

	@Value("${rabbitmq.password}")
	private String password;
	
	@Value("${rabbitmq.username}")
	private String username;
	
	@Value("${rabbitmq.publisherConfirms}")
	private Boolean publisherConfirms;
	
	@Value("${rabbitmq.virtualHost}")
	private String virtualHost;
	
	public String getHost() {
		return host;
	}
	public Integer getPort() {
		return port;
	}
	public String getPassword() {
		return password;
	}
	public String getUsername() {
		return username;
	}
	public Boolean getPublisherConfirms() {
		return publisherConfirms;
	}
	public String getVirtualHost() {
		return virtualHost;
	}
}
