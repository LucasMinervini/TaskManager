package TestController;

import com.example.taskManager.model.Role;
import com.example.taskManager.Controller.RoleController;
import com.example.taskManager.Services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RoleControllerTests {

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

   @Test
public void testGetAllRoles() {
    List<Role> roles = new ArrayList<>();
    Role role = new Role();
    role.setName("ROLE_USER"); 

    when(roleService.getAllRoles()).thenReturn(roles);

    List<Role> response = roleController.getAllRoles();
    assertEquals(1, response.size());
    assertEquals("ROLE_USER", response.get(0).getName());
}

    

    @Test
    public void testCreateRole() {
        Role roleToCreate = new Role();
        Role createdRole = new Role();

        when(roleService.createRole(any(Role.class))).thenReturn(createdRole);

        Role response = roleController.createRole(roleToCreate);
        assertEquals(createdRole.getId(), response.getId());
        assertEquals(createdRole.getName(), response.getName());
    }

    @SuppressWarnings("null")
    @Test
    public void testUpdateRole() {
        Long roleId = 1L;
        Role roleDetails = new Role();
        Role updatedRole = new Role();

        when(roleService.updateRole(eq(roleId), any(Role.class))).thenReturn(updatedRole);

        ResponseEntity<Role> response = roleController.updateRole(roleId, roleDetails);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRole.getName(), response.getBody().getName());
    }

    @Test
    public void testDeleteRole() {
        Long roleId = 1L;

        ResponseEntity<Void> response = roleController.deleteRole(roleId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(roleService, times(1)).deleteRole(roleId);
    }
}
