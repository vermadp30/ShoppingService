/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.stickyio.util.CustomerConstants.*;

@Configuration
public class KafkaCustomerTopicConfig {
    @Bean
    public NewTopic CreateOrderTopic() {
        return TopicBuilder.name(CREATE_ORDER_TOPIC)
                .build();
    }

    @Bean
    public NewTopic SampleRequestTopic() {
        return TopicBuilder.name(SAMPLE_REQUEST_TOPIC)
                .build();
    }

    @Bean
    public NewTopic SampleRequestReplyTopic() {
        return TopicBuilder.name(SAMPLE_REQUEST_REPLY_TOPIC)
                .build();
    }

}