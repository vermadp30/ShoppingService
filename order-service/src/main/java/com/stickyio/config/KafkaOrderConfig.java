/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.config;

import com.stickyio.dto.OrderReplyDto;
import com.stickyio.dto.OrderRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@Slf4j
public class KafkaOrderConfig {
    @Autowired
    KafkaOrderCommonConfig commonConfig;

    @Bean
    public ProducerFactory<String, OrderReplyDto> producerFactoryForOrderReplyDto() {
        return new DefaultKafkaProducerFactory<>(commonConfig.producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, OrderReplyDto> kafkaTemplateForOrderReplyDto() {
        return new KafkaTemplate<String, OrderReplyDto>(producerFactoryForOrderReplyDto());
    }

    @Bean
    public ConsumerFactory<String, OrderReplyDto> consumerFactoryForOrderReply() {
        return new DefaultKafkaConsumerFactory<>(
                commonConfig.consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(OrderReplyDto.class));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderReplyDto>>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderReplyDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryForOrderReply());
        factory.setReplyTemplate(kafkaTemplateForOrderReplyDto());
        return factory;
    }
}
