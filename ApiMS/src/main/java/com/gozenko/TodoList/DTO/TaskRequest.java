package com.gozenko.TodoList.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public class TaskRequest {
    @Schema(example = "Задача такая-то")
    private String naming;
    @Schema(example = "Средний")
    private String priority;
    private Integer userId;

    public TaskRequest() {
    }

    public TaskRequest(String naming, String priority, Integer userId) {
        this.naming = naming;
        this.priority = priority;
        this.userId = userId;
    }

    public TaskRequest(String naming, String priority) {
        this.naming = naming;
        this.priority = priority;
    }

    public String getNaming() {
        return naming;
    }

    public void setNaming(String naming) {
        this.naming = naming;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
