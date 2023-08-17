/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.config;

import static com.stickyio.util.CustomerConstants.CREATE_ORDER_TOPIC;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaCustomerTopicConfig {

  @Bean
  public NewTopic CreateOrderTopic() {
    return TopicBuilder.name(CREATE_ORDER_TOPIC)
        .build();
  }
}