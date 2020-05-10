package com.krimson701.baekchat.configuration;

import com.google.common.collect.ImmutableMap;
import com.krimson701.baekchat.model.ChattingMessage;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${kafka.server}")
    private String server;

    @Bean
    public ProducerFactory<String, ChattingMessage> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs(), null, new JsonSerializer<ChattingMessage>());
    }

    @Bean
    public KafkaTemplate<String, ChattingMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public Map<String, Object> producerConfigs() {

        return ImmutableMap.<String, Object>builder()
                .put("bootstrap.servers", server)//kafka server ip & port
                .put("key.serializer", IntegerSerializer.class)
                .put("value.serializer", JsonSerializer.class)//Object json parser
                .put("group.id", "spring-boot-test") // chatting  group id
                .build();
    }
    //Receiver config
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ChattingMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChattingMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, ChattingMessage> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), null, new JsonDeserializer<>(ChattingMessage.class));
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        return ImmutableMap.<String, Object>builder()
                .put("bootstrap.servers", server)
                .put("key.deserializer", IntegerDeserializer.class)
                .put("value.deserializer", JsonDeserializer.class)
                .put("group.id", "spring-boot-test")
                .build();
    }
}
