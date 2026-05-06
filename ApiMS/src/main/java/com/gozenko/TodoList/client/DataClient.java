package com.gozenko.TodoList.client;

import com.gozenko.TodoList.DTO.TaskResponse;
import com.gozenko.TodoList.DTO.UserResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.List;

@Component
public class DataClient {

    private final RestClient restClient;

    public DataClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public TaskResponse getTaskById(Integer id) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/reports/task")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        ((request, response) -> handleError(response, response.getStatusCode())))
                .onStatus(HttpStatusCode::is5xxServerError,
                        ((request, response) -> handleError(response, response.getStatusCode())))
                .toEntity(TaskResponse.class)
                .getBody();
    }

    public UserResponse getUserById(Integer id) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/reports/user")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        ((request, response) -> handleError(response, response.getStatusCode())))
                .onStatus(HttpStatusCode::is5xxServerError,
                        ((request, response) -> handleError(response, response.getStatusCode())))
                .toEntity(UserResponse.class)
                .getBody();
    }

    public List<TaskResponse> getAllTasksByUser(Integer id) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/reports/tasks/all")
                        .queryParam("userId", id)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        ((request, response) -> handleError(response, response.getStatusCode())))
                .onStatus(HttpStatusCode::is5xxServerError,
                        ((request, response) -> handleError(response, response.getStatusCode())))
                .toEntity(new ParameterizedTypeReference<List<TaskResponse>>() {
                })
                .getBody();
    }

    private void handleError(ClientHttpResponse response, HttpStatusCode status) throws IOException {
        String errorBody = new String(response.getBody().readAllBytes());
        if (status.is4xxClientError()) {
            throw new RuntimeException("Ошибка на клиенте" + errorBody);
        }
        throw new RuntimeException("Ошибка на сервере" + errorBody);
    }
}
