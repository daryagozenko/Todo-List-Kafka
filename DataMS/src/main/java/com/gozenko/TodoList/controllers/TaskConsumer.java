package com.gozenko.TodoList.controllers;

import com.gozenko.TodoList.DTO.TaskRequest;
import com.gozenko.TodoList.models.Task;
import com.gozenko.TodoList.services.TaskService;
import com.gozenko.TodoList.utils.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TaskConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TaskConsumer.class);

    @Autowired
    private TaskService taskService;

    @KafkaListener(topics = "task-topic", groupId = "data-service-group")
    public void createTask(TaskRequest taskReq) {
        logger.info("Input in task-topic-{}",taskReq);
        try {
            Task task = new Task();
            task.setNaming(taskReq.getNaming());

            Priority priority = Priority.fromString(taskReq.getPriority());
            task.setPriority(priority);

            if (taskReq.getUserId() != null) {
                Task res = taskService.createTaskWithUser(task, taskReq.getUserId());
                logger.info("Result task-{}",res);
            } else {
                taskService.createTaskWithoutUser(task);
            }

        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Ошибка в данных: " + e.getMessage());
        }
    }
}