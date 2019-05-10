package cn.ty.mqtt.main.asyn;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ty.mqtt.main.config.ClientConfig;
import cn.ty.mqtt.util.RandomUtil;

/**
 * 
 * Title:Server Description: 服务器向多个客户端推送主题，即不同客户端可向服务器订阅相同主题
 * 
 */
@Service
public class PublishSyncServer implements Runnable {

	@Autowired
	private ClientConfig clientConfig;
	private MqttAsyncClient client;
	
	private void connect() {
			try {
				System.out.println(clientConfig.toString());
				client = new MqttAsyncClient(clientConfig.getHost_path(), clientConfig.getPublishClientId(), new MemoryPersistence());
				MqttConnectOptions options = new MqttConnectOptions();
				options.setCleanSession(false);
				options.setUserName(clientConfig.getUsername());
				options.setPassword(clientConfig.getPassword().toCharArray());
				// 设置超时时间
				options.setConnectionTimeout(10);
				// 设置会话心跳时间
				options.setKeepAliveInterval(20);
//				client.setCallback(new PushCallback());
				IMqttToken mqttToken = client.connect(options);
				mqttToken.waitForCompletion();
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
		IMqttDeliveryToken token = client.publish(clientConfig.getPublisTopic(),message);
		token.waitForCompletion();
//        System.out.println(message+"completely! " + token.isComplete());
	}

	@Override
	public void run() {
		try {
			System.out.println("-----我已进入publish----");
			Thread.sleep(100);
			long begin = System.currentTimeMillis();
			connect();
			// MemoryPersistence设置clientid的保存形式，默认为以内存保存
			for (int i = 0; i < 50000; i++) {
				MqttMessage message = new MqttMessage();
				message.setQos(clientConfig.getPublisQos());
				message.setRetained(false);
				message.setPayload((i + ":" + RandomUtil.getRandomString(12)).getBytes());
				publish(message);
			}
			System.out.println("发送结束"+(System.currentTimeMillis() - begin) / 1000 + "s");
		} catch (MqttException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
