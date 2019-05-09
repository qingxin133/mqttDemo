package cn.ty.mqtt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.ty.mqtt.main.Publish;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class MqttDemoApplicationTests {
	public static final String TOPIC1 = "toclient/1";
	public static final String TOPIC2 = "toclient/2";
	public static final String TOPIC3 = "toclient/3";
//	@Test
	public void contextLoads() {
	
		
	}
	
	public static void main(String[] args) {
		try {
			ExecutorService  exService = Executors.newCachedThreadPool();
			exService.submit(new Publish("server1",TOPIC1,0));
			Thread.sleep(100);
			exService.submit(new Publish("server2",TOPIC2,1));
			Thread.sleep(100);
			exService.submit(new Publish("server3",TOPIC3,2));
//			new Publish(TOPIC1,0).start();
//			new Publish(TOPIC2,1).start();
//			new Publish(TOPIC3,2).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
