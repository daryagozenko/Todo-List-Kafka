package com.gozenko.TodoList.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.List;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic taskTopic() {
        return TopicBuilder.name("task-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic userTopic() {
        return TopicBuilder.name("user-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
