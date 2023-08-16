/* * Copyright 2023-2024 the original author or authors. * * TBD */

package com.stickyio.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.stickyio.util.CustomerConstants.*;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic TrackOrderExternalRequestTopic(){
        return TopicBuilder.name(TRACK_EXTERNAL_ORDER_REQUEST_TOPIC)
                .build();
    }

    @Bean
    public NewTopic TrackOrderExternalReplyTopic(){
        return TopicBuilder.name(TRACK_EXTERNAL_ORDER_RESPONSE_TOPIC)
                .build();
    }

    @Bean
    public NewTopic TrackOrderRequestTopic(){
        return TopicBuilder.name(TRACK_ORDER_REQUEST_TOPIC)
                .build();
    }

    @Bean
    public NewTopic TrackOrderReplyTopic(){
        return TopicBuilder.name(TRACK_ORDER_REPLY_TOPIC)
                .build();
    }
}
