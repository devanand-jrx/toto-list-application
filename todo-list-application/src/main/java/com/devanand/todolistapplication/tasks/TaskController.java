package com.devanand.todolistapplication.tasks;


import com.devanand.todolistapplication.exception.UserNotFoundException;
import com.devanand.todolistapplication.repo.TaskRepository;
import com.devanand.todolistapplication.tasks.Task;
import com.devanand.todolistapplication.tasks.TaskService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.print.Book;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TaskController {

    private TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;

    }

    @GetMapping("/tasks")
    public List<Task> retrieveAllUsers(){
        return taskService.findAll();
    }

    @PostMapping("addTask")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Task savedTask = taskService.save(task);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedTask.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @GetMapping("/getAllTasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/getTaskById/{id}")
    public EntityModel<Task> getTaskById(@PathVariable int id) {
        Task task = taskService.findOne(id);

        if(task==null)
            throw new UserNotFoundException("id:"+id);

        EntityModel<Task> entityModel = EntityModel.of(task);

        WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).getAllTasks());
        entityModel.add(link.withRel("all-tasks"));

        return entityModel;
    }


    @PutMapping("/updateTask/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/deleteTask/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}