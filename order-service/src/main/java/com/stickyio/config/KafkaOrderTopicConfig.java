/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaOrderTopicConfig {
    @Bean
    public NewTopic CreateOrderReplyTopic(){
        return TopicBuilder.name("create-order-reply")
                .build();
    }
}
