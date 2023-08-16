/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.config;

import com.stickyio.dto.TrackingRequestDto;
import com.stickyio.dto.TrackingResponseDto;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import static com.stickyio.util.CustomerConstants.TRACK_EXTERNAL_ORDER_RESPONSE_TOPIC;

@Configuration
public class KafkaTrackingConfig {

    @Autowired
    KafkaOrderCommonConfig commonConfig;

    @Bean
    public ProducerFactory<String, TrackingResponseDto> producerFactoryForTrackingResponseDto() {
        return new DefaultKafkaProducerFactory<>(commonConfig.producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, TrackingResponseDto> kafkaTemplateForTrackingResponseDto() {
        return new KafkaTemplate<String, TrackingResponseDto>(producerFactoryForTrackingResponseDto());
    }

    @Bean
    public ConsumerFactory<String, TrackingResponseDto> consumerFactoryForTrackingResponse() {
        return new DefaultKafkaConsumerFactory<>(
                commonConfig.consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(TrackingResponseDto.class));
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, TrackingResponseDto>>
    kafkaListenerContainerFactoryForTrackingResponse() {
        ConcurrentKafkaListenerContainerFactory<String, TrackingResponseDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryForTrackingResponse());
        factory.setReplyTemplate(kafkaTemplateForTrackingResponseDto());
        return factory;
    }

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
    public KafkaMessageListenerContainer<String, TrackingResponseDto> replyContainerForExternalTracking(
            ConsumerFactory<String, TrackingResponseDto> cf) {
        ContainerProperties containerProperties = new ContainerProperties(TRACK_EXTERNAL_ORDER_RESPONSE_TOPIC);
        return new KafkaMessageListenerContainer<>(cf, containerProperties);
    }
}
