/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.config;

import com.stickyio.dto.TrackingRequestDto;
import com.stickyio.dto.TrackingResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import static com.stickyio.util.CustomerConstants.TRACK_ORDER_REPLY_TOPIC;

@Configuration
@Slf4j
public class KafkaTrackingConfig {

    @Autowired
    KafkaConsumerCommonConfig commonConfig;

    @Bean
    public ProducerFactory<String, TrackingRequestDto> producerFactoryForTrackingRequestDto() {
        return new DefaultKafkaProducerFactory<>(commonConfig.producerConfigs());
    }

    @Bean
    public ReplyingKafkaTemplate<String, TrackingRequestDto, TrackingResponseDto> replyingTemplateForTracking(
            ProducerFactory<String, TrackingRequestDto> pf,
            KafkaMessageListenerContainer<String, TrackingResponseDto> container) {
        return new ReplyingKafkaTemplate<>(pf, container);
    }

    @Bean
    public KafkaMessageListenerContainer<String, TrackingResponseDto> replyContainerForTracking(
            ConsumerFactory<String, TrackingResponseDto> cf) {
        ContainerProperties containerProperties = new ContainerProperties(TRACK_ORDER_REPLY_TOPIC);
        return new KafkaMessageListenerContainer<>(cf, containerProperties);
    }

    @Bean
    public ConsumerFactory<String, TrackingResponseDto> consumerFactoryForTracking() {
        return new DefaultKafkaConsumerFactory<>(
                commonConfig.consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(TrackingResponseDto.class));
    }
}