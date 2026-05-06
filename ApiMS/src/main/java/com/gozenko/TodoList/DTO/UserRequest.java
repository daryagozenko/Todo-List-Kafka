package com.gozenko.TodoList.DTO;

public class UserRequest {
    private String name;
    private String surname;

    public UserRequest(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public UserRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
