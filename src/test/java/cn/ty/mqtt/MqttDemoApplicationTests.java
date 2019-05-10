package cn.ty.mqtt;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.ty.mqtt.main.asyn.ConsumerSyncServer;
import cn.ty.mqtt.main.asyn.PublishSyncServer;
import cn.ty.mqtt.main.base.PublishServer;
import cn.ty.mqtt.main.config.ClientConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqttDemoApplicationTests {
	
	public static final String TOPIC1 = "toclient/3";
	@Autowired
	private ClientConfig clientConfig;
	
	@Autowired
	private ConsumerSyncServer consumerServer;
	@Autowired
	private PublishSyncServer publishServer;
	@Test
	public void dosyncMain() {
		try {
//			consumerServer.doMain();
			ExecutorService  exService = Executors.newCachedThreadPool();
//			exService.submit(new PrintThread("123123"));
			exService.submit(consumerServer);
			Thread.sleep(200);
			exService.submit(publishServer);
			while(true) {
				Thread.sleep(1000);
			}
//			Thread.sleep(1000);
//			exService.submit(new PublishSyncServer("client125",TOPIC1,0,"admin","public"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void doConfig() {
		System.out.println("--------------"+clientConfig.getUsername());
		String[] topics = clientConfig.getConsumerTopics().toArray(new String[0]);
		int qoss[] = Arrays.stream(clientConfig.getConsumerQos().toArray(new String[0])).mapToInt(Integer::valueOf).toArray();
		System.out.println(Arrays.toString(topics));
		System.out.println(Arrays.toString(qoss));
	}
	
	
	public void doMain() {
		try {
//			ExecutorService  exService = Executors.newCachedThreadPool();
//			exService.submit(new PublishServer("server1",TOPIC1,0));
//			Thread.sleep(100);
//			exService.submit(new PublishServer("server2",TOPIC2,1));
//			Thread.sleep(100);
//			exService.submit(new PublishServer("server3",TOPIC3,2));
//			new Publish(TOPIC1,0).start();
//			new Publish(TOPIC2,1).start();
//			new Publish(TOPIC3,2).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
 

}
