package it.tcgroup.damoconsumer;

import it.tcgroup.sensor.SensorData;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@CommonsLog
@SpringBootApplication
@EnableKafka
public class DamoConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DamoConsumerApplication.class, args);
	}


	@Autowired
	private SensorDetail sensorDetail;


	@KafkaListener(topics = "${kafka.topic}", groupId = "group1", clientIdPrefix = "client1",
			containerFactory = "sensorDataKafkaListenerContainerFactory")
	public void listner(
			@Payload SensorData payload,
			@Header ConsumerRecord<String, SensorData> header
	){

		if(sensorDetail.getRecord().containsKey(payload.getLocation())){
			SensorDetail.Dati dati = sensorDetail.getRecord().get(payload.getLocation());
			dati.setLast(Integer.valueOf(payload.getValue()));
			if(Integer.valueOf(payload.getValue()) < dati.getMin()){
				dati.setMin(Integer.valueOf(payload.getValue()));
			}
			if(Integer.valueOf(payload.getValue()) > dati.getMax() ){
				dati.setMax(Integer.valueOf(payload.getValue()));
			}
		}else{
			SensorDetail.Dati dati = new SensorDetail.Dati();
			dati.setLast(Integer.valueOf(payload.getValue()));
			dati.setMin(Integer.valueOf(payload.getValue()));
			dati.setMax(Integer.valueOf(payload.getValue()));
			sensorDetail.getRecord().put(payload.getLocation(), dati);
		}

	}

}
