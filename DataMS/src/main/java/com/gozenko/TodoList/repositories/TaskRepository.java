package com.gozenko.TodoList.repositories;

import com.gozenko.TodoList.models.Task;
import com.gozenko.TodoList.utils.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findAllByUserId(Integer id);
    @Query("SELECT t FROM Task t WHERE t.priority = :priority")
    List<Task> findAllByPriority(@Param("priority") Priority priority);
}
