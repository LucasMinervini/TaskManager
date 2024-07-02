package TestController;

import com.example.taskManager.Controller.TaskController;
import com.example.taskManager.Services.TaskServices;
import com.example.taskManager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import javax.management.relation.RelationNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TaskControllerTests {

    @Mock
    private TaskServices taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    

    @Test
    public void testGetTaskById() throws RelationNotFoundException {
        Long taskId = 1L;
        Task task = new Task(); 

        when(taskService.getTaskById(taskId)).thenReturn(Optional.of(task));

        ResponseEntity<Task> response = taskController.getTaskById(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    

    @Test
    public void testUpdateTask() {
        Long taskId = 1L;
        Task updatedTask = new Task();

        when(taskService.updateTask(taskId, updatedTask)).thenReturn(updatedTask);

        ResponseEntity<Task> response = taskController.updateTask(taskId, updatedTask);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTask, response.getBody());
    }

    @Test
    public void testDeleteTask() throws RelationNotFoundException {
        Long taskId = 1L;

        ResponseEntity<Void> response = taskController.deleteTask(taskId);

        verify(taskService, times(1)).deleteTask(taskId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

