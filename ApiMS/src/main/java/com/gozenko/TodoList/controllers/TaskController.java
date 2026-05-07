package com.gozenko.TodoList.controllers;

import com.gozenko.TodoList.DTO.TaskRequest;
import com.gozenko.TodoList.DTO.TaskResponse;
import com.gozenko.TodoList.client.DataClient;
import com.gozenko.TodoList.service.TaskKafkaService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskKafkaService taskKafkaService;
    @Autowired
    private DataClient dataClient;

    @PostMapping("/create")
    @Operation(summary = "создать задачу")
    public ResponseEntity<?> createTask(@RequestBody TaskRequest taskReq) {
        logger.info("Input task request-{}",taskReq);
        try {
            taskKafkaService.sendTask(taskReq);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Task created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера: " + e.getMessage());
        }
    }

    @GetMapping("/task")
    @Operation(summary = "получить задачу по id")
    public ResponseEntity<?> getTaskById(@RequestParam Integer id) {
        logger.info("Input id-{}", id);
        TaskResponse taskResponse = dataClient.getTaskById(id);
        logger.info("result TaskResponse-{}",taskResponse);

        return ResponseEntity.ok(taskResponse);
    }

    @GetMapping("/tasks/all")
    @Operation(summary = "Отчет - все задачи пользователя")
    public ResponseEntity<?> getAllTasksByUser(@RequestParam Integer id) {
        logger.info("Input id in report-{}", id);
        List<TaskResponse> tasks = dataClient.getAllTasksByUser(id);
        logger.info("result list tasks-{}",tasks);

        return ResponseEntity.ok(tasks);
    }
}