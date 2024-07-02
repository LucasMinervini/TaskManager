package com.example.taskManager.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.taskManager.model.Task;
import com.example.taskManager.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

import javax.management.relation.RelationNotFoundException;

@Service
public class TaskServices {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow();

        task.setTitle(taskDetails.getTitle());
        task.setDescripttion(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) throws RelationNotFoundException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RelationNotFoundException("Task not found for this id :: " + id));
        taskRepository.delete(task);
    }
}