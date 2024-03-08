package it.tcgroup.damoconsumer;

import it.tcgroup.sensor.SensorData;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@CommonsLog
@Component
public class SensorDataKafkaConfiguration {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SensorData> sensorDataKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SensorData> factory =
                new ConcurrentKafkaListenerContainerFactory<String, SensorData>();
        factory.setConsumerFactory(sensorDataConsumerFactory());
        return factory;
    }

    @Bean
    ConsumerFactory<String, SensorData> sensorDataConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),
                new JsonDeserializer<>(SensorData.class));
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:29092");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }


}
