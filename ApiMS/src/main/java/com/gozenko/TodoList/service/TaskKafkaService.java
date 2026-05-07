package com.gozenko.TodoList.service;

import com.gozenko.TodoList.DTO.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskKafkaService {

    private static final String TASK_TOPIC = "task-topic";

    @Autowired
    private KafkaTemplate<String, TaskRequest> kafkaTemplate;

    public void sendTask(TaskRequest taskRequest) {
        String key = taskRequest.getNaming() + " " + taskRequest.getPriority();
        kafkaTemplate.send(TASK_TOPIC, key, taskRequest);
    }
}
