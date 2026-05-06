package com.gozenko.TodoList.controllers;

import com.gozenko.TodoList.DTO.UserRequest;
import com.gozenko.TodoList.models.User;
import com.gozenko.TodoList.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    private static final Logger logger = LoggerFactory.getLogger(UserConsumer.class);

    @Autowired
    private UserService userService;

    @KafkaListener(topics = "user-topic", groupId = "data-service-group")
    public void createUser(UserRequest userReq) {
        logger.info("Input in user-topic-{}", userReq);
        try {
            User user = new User();
            user.setName(userReq.getName());
            user.setSurname(userReq.getSurname());

            User res = userService.createUser(user);
            logger.info("Result user-{}", res);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Ошибка в данных: " + e.getMessage());
        }
    }

}
