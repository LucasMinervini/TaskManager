package com.example.taskManager.Services;

import com.example.taskManager.model.Role;
import com.example.taskManager.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @SuppressWarnings("deprecation")
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
public void testGetAllRoles() {
    // Simular datos de roles
    Role role1 = new Role();
    role1.setName("ROLE_USER");

    Role role2 = new Role();
    role2.setName("ROLE_ADMIN");

    List<Role> roles = Arrays.asList(role1, role2);

    // Mockear el comportamiento del repositorio
    when(roleRepository.findAll()).thenReturn(roles);

    // Llamar al método del servicio que quieres probar
    List<Role> result = roleService.getAllRoles();

    // Verificar que la lista tiene el tamaño esperado
    assertEquals(2, result.size());

    // Verificar que los nombres de los roles coinciden
    assertEquals("ROLE_USER", result.get(0).getName());
    assertEquals("ROLE_ADMIN", result.get(1).getName());
}

    @Test
    public void testGetRoleById() {
        Long roleId = 1L;
        Role mockRole = new Role();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(mockRole));

        Optional<Role> result = roleService.getRoleById(roleId);

        assertEquals(mockRole, result.orElse(null));
    }

    

    @Test
public void testUpdateRole() {
    Long roleId = 1L;
    String initialRoleName = "ROLE_USER";
    String updatedRoleName = "ROLE_UPDATED";

    // Creamos el rol existente
    Role existingRole = new Role();
    existingRole.setId(roleId);
    existingRole.setName(initialRoleName);

    // Detalles del rol actualizado
    Role updatedRoleDetails = new Role();
    updatedRoleDetails.setId(roleId); // Mismo ID que el rol existente
    updatedRoleDetails.setName(updatedRoleName); // Nuevo nombre para el rol

    // Configuramos el comportamiento del repositorio mock
    when(roleRepository.findById(roleId)).thenReturn(Optional.of(existingRole));
    when(roleRepository.save(existingRole)).thenReturn(updatedRoleDetails);

    // Ejecutamos el método a probar
    Role updatedRole = roleService.updateRole(roleId, updatedRoleDetails);

    // Verificamos que el nombre del rol actualizado sea correcto
    assertEquals(updatedRoleName, updatedRole.getName());
}

    @Test
    public void testDeleteRole() {
        Long roleId = 1L;
        Role roleToDelete = new Role();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(roleToDelete));

        roleService.deleteRole(roleId);

        verify(roleRepository, times(1)).delete(roleToDelete);
    }
}
