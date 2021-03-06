package cn.ty.mqtt.main.asyn;

import java.util.Arrays;
import java.util.concurrent.ScheduledExecutorService;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ty.mqtt.main.callback.PushCallback;
import cn.ty.mqtt.main.config.ClientConfig;

/**
 * 异步消费类
 * @author super james
 *
 */
@Service
public class ConsumerSyncServer implements Runnable{

	@Autowired
	private ClientConfig clientConfig;
	
	private MqttAsyncClient client;
	private MqttConnectOptions options;

	@Override
	public void run() {
		try {
			System.out.println(clientConfig.toString());
			// host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
			client = new MqttAsyncClient(clientConfig.getHost_path(), clientConfig.getCosumerClientId(), new MemoryPersistence());
			// MQTT的连接设置
			options = new MqttConnectOptions();
			// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
			options.setCleanSession(true);
			// 设置连接的用户名
			options.setUserName(clientConfig.getUsername());
			// 设置连接的密码
			options.setPassword(clientConfig.getPassword().toCharArray());
			// 设置超时时间 单位为秒
			options.setConnectionTimeout(10);
			// 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
			options.setKeepAliveInterval(20);
			// 设置回调
			client.setCallback(new PushCallback());
//			MqttTopic topic = client.getTopic(TOPIC);
			// setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
//			options.setWill(topic, "close".getBytes(), 2, true);

			IMqttToken mqttToken = client.connect(options);
			mqttToken.waitForCompletion();
			
			// 订阅消息
			String[] topics = clientConfig.getConsumerTopics().toArray(new String[0]);
			int qoss[] = Arrays.stream(clientConfig.getConsumerQos().toArray(new String[0])).mapToInt(Integer::valueOf).toArray();
			client.subscribe(topics, qoss);
			System.out.println("------启动consumer结束------");

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
	}
 

}
