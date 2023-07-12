package com.devanand.todolistapplication.tasks;


import com.devanand.todolistapplication.repo.TaskRepository;
import jakarta.persistence.EntityNotFoundException;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class TaskService {

    private static List<Task> tasks = new ArrayList<>();


    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task save(Task task){
        tasks.add(task);
        return task;
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    }

    public Task updateTask(Long id, Task task) {
        Task existingTask = getTaskById(id);
        existingTask.setName(task.getName());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }


    public List<Task> findAll() {
        return tasks;
    }


    public Task findOne(int id) {
        Predicate<? super Task> predicate = user -> user.getId().equals(id);
        return tasks.stream().filter(predicate).findFirst().orElse(null);

    }

    public void deleteById(int id){
        Predicate<? super Task> predicate = user -> user.getId().equals(id);
        tasks.removeIf(predicate);
    }
}
