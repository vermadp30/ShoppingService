/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.config;

import static com.stickyio.util.CustomerConstants.CREATE_ORDER_REPLY_TOPIC;

import com.stickyio.dto.OrderReplyDto;
import com.stickyio.dto.OrderRequestDto;
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

@Configuration
@Slf4j
public class KafkaOrderConfig {

  @Autowired
  KafkaConsumerCommonConfig commonConfig;

  @Bean
  public ProducerFactory<String, OrderRequestDto> producerFactoryForOrderRequestDto() {
    return new DefaultKafkaProducerFactory<>(commonConfig.producerConfigs());
  }

  @Bean
  public ReplyingKafkaTemplate<String, OrderRequestDto, OrderReplyDto> replyingTemplateForOrder(
      ProducerFactory<String, OrderRequestDto> pf,
      KafkaMessageListenerContainer<String, OrderReplyDto> container) {
    return new ReplyingKafkaTemplate<>(pf, container);
  }

  @Bean
  public KafkaMessageListenerContainer<String, OrderReplyDto> replyContainerForOrder(
      ConsumerFactory<String, OrderReplyDto> cf) {
    ContainerProperties containerProperties = new ContainerProperties(CREATE_ORDER_REPLY_TOPIC);
    return new KafkaMessageListenerContainer<>(cf, containerProperties);
  }

  @Bean
  public ConsumerFactory<String, OrderReplyDto> consumerFactoryForOrder() {
    return new DefaultKafkaConsumerFactory<>(
        commonConfig.consumerConfigs(),
        new StringDeserializer(),
        new JsonDeserializer<>(OrderReplyDto.class));
  }
}
