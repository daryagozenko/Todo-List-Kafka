package com.gozenko.TodoList.controllers;

import com.gozenko.TodoList.DTO.UserRequest;
import com.gozenko.TodoList.DTO.UserResponse;
import com.gozenko.TodoList.client.DataClient;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private KafkaTemplate<String, UserRequest> kafkaTemplate;
    @Autowired
    private DataClient dataClient;

    @PostMapping("/create")
    @Operation(summary = "создать пользователя")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userReq){
        try {
            kafkaTemplate.send("user-topic", userReq);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера: " + e.getMessage());
        }
    }

    @GetMapping("/user")
    @Operation(summary = "получить пользователя по id")
    public ResponseEntity<?> getUserById(@RequestParam Integer id) {
        logger.info("Input id-{}", id);
        UserResponse userResponse = dataClient.getUserById(id);
        logger.info("result UserResponse-{}",userResponse);

        return ResponseEntity.ok(userResponse);
    }
}
