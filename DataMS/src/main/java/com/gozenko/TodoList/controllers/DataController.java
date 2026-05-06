package com.gozenko.TodoList.controllers;

import com.gozenko.TodoList.DTO.TaskResponse;
import com.gozenko.TodoList.DTO.UserResponse;
import com.gozenko.TodoList.models.Task;
import com.gozenko.TodoList.models.User;
import com.gozenko.TodoList.services.TaskService;
import com.gozenko.TodoList.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class DataController {

    private final TaskService taskService;
    private final UserService userService;

    public DataController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/task")
    @Operation(summary = "получить задачу по id")
    public ResponseEntity<?> getTaskById(@RequestParam Integer id) {
        try {
            Task task = taskService.findById(id);
            return ResponseEntity.ok(new TaskResponse(task));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/tasks/all")
    @Operation(summary = "Отчет - все задачи пользователя")
    public ResponseEntity<?> getAllTasksByUser(@RequestParam Integer userId) {
        try {
            List<Task> tasks;
            if (userId != null) {
                tasks = taskService.findByUserId(userId);
            } else {
                tasks = taskService.getAll();
            }
            return ResponseEntity.ok(TaskResponse.fromTasks(tasks));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/user")
    @Operation(summary = "получить пользователя по id")
    public ResponseEntity<?> getUserById(@RequestParam Integer id){
        try{
            User createdUser = userService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(createdUser));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
