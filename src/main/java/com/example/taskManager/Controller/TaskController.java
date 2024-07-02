package com.example.taskManager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.taskManager.Services.TaskServices;
import com.example.taskManager.model.Task;

import java.util.List;

import javax.management.relation.RelationNotFoundException;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskServices taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable(value = "id") Long taskId) throws RelationNotFoundException {
        Task task = taskService.getTaskById(taskId)
                .orElseThrow(() -> new RelationNotFoundException("Task not found for this id :: " + taskId));
        return ResponseEntity.ok().body(task);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable(value = "id") Long taskId, @RequestBody Task taskDetails) {
        Task updatedTask = taskService.updateTask(taskId, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable(value = "id") Long taskId) throws RelationNotFoundException {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
