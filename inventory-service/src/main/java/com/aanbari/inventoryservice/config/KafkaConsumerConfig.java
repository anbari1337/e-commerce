package com.aanbari.inventoryservice.config;

import com.aanbari.inventoryservice.dto.OrderEvent;
import com.aanbari.inventoryservice.dto.ProductEvent;
import com.aanbari.inventoryservice.dto.ProductInventoryEvent;
import com.aanbari.inventoryservice.exception.InventoryNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrapServers;

    private Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // 🔹 Wrap the deserializers inside ErrorHandlingDeserializer
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        // 🔹 Define actual deserializers
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return props;
    }

    private <T> ConsumerFactory<String, T> createConsumerFactory(Class<T> eventType) {
        Map<String, Object> config = new HashMap<>(consumerConfig());
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, eventType.getName()); // Ensure correct deserialization

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(eventType));
    }

    private <T> ConcurrentKafkaListenerContainerFactory<String, T> createListenerContainerFactory(Class<T> eventType){
        ConcurrentKafkaListenerContainerFactory<String, T> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createConsumerFactory(eventType));
        return factory;
    }

    @Bean
    public KafkaListenerErrorHandler kafkaErrorHandler() {
        return (message, exception) -> {
            if(exception.getCause() instanceof InventoryNotFoundException){
                log.info("Kafka error: " + exception.getCause().getMessage());
            }else{
                log.error("Unexpected error processing Kafka message: {}", exception.getMessage(), exception);
            }
            return null; // Skip this message
        };
    }

    @Bean
    public ConsumerFactory<String, OrderEvent> orderEventConsumerFactory(){
        return createConsumerFactory(OrderEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderEvent> orderEventListenerFactory(){
        return createListenerContainerFactory(OrderEvent.class);
    }

    @Bean
    public ConsumerFactory<String, ProductInventoryEvent> productInventoryEventConsumerFactory(){
        return createConsumerFactory(ProductInventoryEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProductInventoryEvent> productInventoryEventListenerFactory(){
        return createListenerContainerFactory(ProductInventoryEvent.class);
    }

    @Bean
    public ConsumerFactory<String, ProductEvent> newProductEventConsumerFactory(){
        return createConsumerFactory(ProductEvent.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProductEvent> newProductEventListenerFactory(){
        return createListenerContainerFactory(ProductEvent.class);
    }

}
