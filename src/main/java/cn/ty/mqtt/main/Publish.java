package cn.ty.mqtt.main;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import cn.ty.mqtt.main.callback.PushCallback;
import cn.ty.mqtt.util.RandomUtil;

/**
 * 
 * Title:Server Description: 服务器向多个客户端推送主题，即不同客户端可向服务器订阅相同主题
 * 
 */
public class Publish implements Runnable {

	public static final String HOST = "tcp://192.168.80.128:1883";
//	public static final String TOPIC1 = "toclient/1";
//	public static final String TOPIC2 = "toclient/2";
//	public static final String TOPIC3 = "toclient/3";


	private MqttTopic topic;
	private String userName = "admin";
	private String passWord = "password";
	
	private String clientid ;
	private int qos;
	private String topicName;
	private MqttClient client;
	


	public Publish(String clientid,String topicName,int qos){
		this.clientid = clientid;
		this.topicName = topicName;
		this.qos = qos;
	}

	private void connect() {
			try {
				client = new MqttClient(HOST, clientid, new MemoryPersistence());
				MqttConnectOptions options = new MqttConnectOptions();
				options.setCleanSession(false);
				options.setUserName(userName);
				options.setPassword(passWord.toCharArray());
				// 设置超时时间
				options.setConnectionTimeout(10);
				// 设置会话心跳时间
				options.setKeepAliveInterval(20);
//            client.setCallback(new PushCallback());
				client.connect(options);
				topic = client.getTopic(topicName);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (MqttSecurityException e) {
				e.printStackTrace();
			} catch (MqttException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		 
	}

	public void publish(MqttMessage message) throws MqttPersistenceException, MqttException {
		MqttDeliveryToken token = topic.publish(message);
		token.waitForCompletion();
//        System.out.println(message+"completely! " + token.isComplete());
	}

	@Override
	public void run() {
		try {
			long begin = System.currentTimeMillis();
			connect();
			// MemoryPersistence设置clientid的保存形式，默认为以内存保存
			for (int i = 0; i < 50000; i++) {
				MqttMessage message = new MqttMessage();
				message.setQos(qos);
				message.setRetained(false);
				message.setPayload((i + ":" + RandomUtil.getRandomString(12)).getBytes());
				publish(message);
			}
			System.out.println((System.currentTimeMillis() - begin) / 1000 + "s");
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
//        server.message = new MqttMessage();
//        server.message.setQos(1);
//        server.message.setRetained(false);
//        server.message.setPayload("给客户端125推送的信息2244".getBytes());
//        server.publish(server.topic125 , server.message);
//        
//        server.message = new MqttMessage();
//        server.message.setQos(1);
//        server.message.setRetained(false);
//        server.message.setPayload("给客户端126推送的信息5555555".getBytes());
//        server.publish(server.topic126 , server.message);

	}

}
