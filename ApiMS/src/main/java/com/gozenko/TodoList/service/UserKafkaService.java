package com.gozenko.TodoList.service;

import com.gozenko.TodoList.DTO.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserKafkaService {

    private static final String USER_TOPIC = "user-topic";

    @Autowired
    private KafkaTemplate<String, UserRequest> kafkaTemplate;

    public void sendUser(UserRequest userRequest) {
        String key = userRequest.getName() + " " + userRequest.getSurname();
        kafkaTemplate.send(USER_TOPIC, key, userRequest);
    }

}
