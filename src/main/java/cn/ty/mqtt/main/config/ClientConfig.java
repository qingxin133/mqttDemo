package cn.ty.mqtt.main.config;


import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @author super james
 *
 */
@Component
@ConfigurationProperties(prefix = "mqtt.client")
public class ClientConfig {

	private String host_path;
	private String username;
	private String password;
	
	private String publishClientId;
	private int publisQos;
	private String publisTopic;

	private String cosumerClientId;
	private List<String> consumerTopics;
	private List<String> consumerQos;

	public ClientConfig() {
		// TODO Auto-generated constructor stub
	}

 
	public String getHost_path() {
		return host_path;
	}

	public void setHost_path(String host_path) {
		this.host_path = host_path;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPublisQos() {
		return publisQos;
	}

	public void setPublisQos(int publisQos) {
		this.publisQos = publisQos;
	}

	public String getPublisTopic() {
		return publisTopic;
	}

	public void setPublisTopic(String publisTopic) {
		this.publisTopic = publisTopic;
	}

	public String getPublishClientId() {
		return publishClientId;
	}

	public void setPublishClientId(String publishClientId) {
		this.publishClientId = publishClientId;
	}

	public String getCosumerClientId() {
		return cosumerClientId;
	}

	public void setCosumerClientId(String cosumerClientId) {
		this.cosumerClientId = cosumerClientId;
	}


	public List<String> getConsumerTopics() {
		return consumerTopics;
	}


	public void setConsumerTopics(List<String> consumerTopics) {
		this.consumerTopics = consumerTopics;
	}


	public List<String> getConsumerQos() {
		return consumerQos;
	}


	public void setConsumerQos(List<String> consumerQos) {
		this.consumerQos = consumerQos;
	}


	@Override
	public String toString() {
		return "ClientConfig [host_path=" + host_path + ", username=" + username + ", password=" + password
				+ ", publishClientId=" + publishClientId + ", publisQos=" + publisQos + ", publisTopic=" + publisTopic
				+ ", cosumerClientId=" + cosumerClientId + ", consumerTopics=" + consumerTopics + ", consumerQos="
				+ consumerQos + "]";
	}
	
	

}
