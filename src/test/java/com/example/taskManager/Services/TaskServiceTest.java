package com.example.taskManager.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.management.relation.RelationNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.taskManager.model.Task;
import com.example.taskManager.repository.TaskRepository;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServices taskService;

    private Task task1;
    private Task task2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicialización de datos de prueba
        task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");

        task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");
    }

    @Test
    public void testGetAllTasks() {
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        assertEquals(task1.getTitle(), result.get(0).getTitle());
        assertEquals(task2.getTitle(), result.get(1).getTitle());
    }


    @Test
    public void testGetTaskById() {
        Long id = 1L;

        when(taskRepository.findById(id)).thenReturn(Optional.of(task1));

        Optional<Task> result = taskService.getTaskById(id);

        assertEquals(true, result.isPresent());
        assertEquals(task1.getTitle(), result.get().getTitle());
    }

    @Test
    public void testCreateTask() {
        Task newTask = new Task();

        when(taskRepository.save(any(Task.class))).thenReturn(newTask);

        Task createdTask = taskService.createTask(newTask);

        assertEquals(newTask.getTitle(), createdTask.getTitle());
    }

    @Test
public void testUpdateTask() {
    Long id = 1L;
    Task updatedTaskDetails = new Task();
    updatedTaskDetails.setTitle("Updated Task");
    updatedTaskDetails.setDescripttion("Updated description");
   
    when(taskRepository.findById(id)).thenReturn(Optional.of(task1));
    when(taskRepository.save(any(Task.class))).thenReturn(updatedTaskDetails);

    Task updatedTask = taskService.updateTask(id, updatedTaskDetails);

    assertEquals(updatedTaskDetails.getTitle(), updatedTask.getTitle());
    assertEquals(updatedTaskDetails.getDescription(), updatedTask.getDescription());
    assertEquals(updatedTaskDetails.isCompleted(), updatedTask.isCompleted());
}

    @Test
    public void testDeleteTask() throws RelationNotFoundException {
        Long id = 1L;

        when(taskRepository.findById(id)).thenReturn(Optional.of(task1));

        taskService.deleteTask(id);

        verify(taskRepository, times(1)).delete(task1);
    }
}
