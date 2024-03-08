package it.tcgroup.sensor;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import java.security.Key;
import java.util.Random;
@CommonsLog
@EnableKafka
@SpringBootApplication
public class SensorApplication {

	@Value("${kafka.topic}")
	private String kafkaTopic;
	@Value("${sensor.delay}")
	private Integer sensorDelay;

	@Value("${sensor.id}")
	private String sensorId;
	@Value("${sensor.name}")
	private String sensorName;
	@Value("${sensor.city}")
	private String sensorCity;

	@Value("${sensor.value.min}")
	private int min;
	@Value("${sensor.value.max}")
	private int max;

	public static void main(String[] args) {
		SpringApplication.run(SensorApplication.class, args);
	}


	@Autowired
	private KafkaTemplate<String, SensorData> kafkaTemplate;

	@Bean
	public NewTopic topicSensor(){
		return TopicBuilder.name(kafkaTopic)
				.partitions(10)
				.replicas(1)
				.build();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void sendData() throws InterruptedException {
		Random random = new Random();
		String key = sensorName + sensorId;
		SensorData value = new SensorData(null, sensorCity, null);

		while (true){
			Thread.sleep(sensorDelay);
			value.setValue(String.valueOf(random.nextInt(max-min) + min));
			kafkaTemplate.send(kafkaTopic, key, value);
			log.info("send value: " + value.getValue());
		}
	}

}
