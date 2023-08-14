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

    @Bean
    public NewTopic TrackOrderExternalRequestTopic(){
        return TopicBuilder.name("track-order-ext-request")
                .build();
    }

    @Bean
    public NewTopic TrackOrderExternalReplyTopic(){
        return TopicBuilder.name("track-order-ext-reply")
                .build();
    }

    @Bean
    public NewTopic TrackOrderRequestTopic(){
        return TopicBuilder.name("track-order-request")
                .build();
    }

    @Bean
    public NewTopic TrackOrderReplyTopic(){
        return TopicBuilder.name("track-order-reply")
                .build();
    }
}
