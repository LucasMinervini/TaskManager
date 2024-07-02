package com.example.taskManager.Services;

import com.example.taskManager.model.User;
import com.example.taskManager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetAllUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    public void testGetUserById() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserById(userId);

        // Assert
        assertEquals(user.getId(), result.get().getId());
        assertEquals(user.getUsername(), result.get().getUsername());
        assertEquals(user.getPassword(), result.get().getPassword());
    }

    @Test
    public void testCreateUser() {
        // Arrange
        User userToCreate = new User();
        when(passwordEncoder.encode(userToCreate.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(userToCreate);

        // Act
        User createdUser = userService.createUser(userToCreate);

        // Assert
        assertEquals(userToCreate.getUsername(), createdUser.getUsername());
        assertEquals("encodedPassword", createdUser.getPassword());
    }

    @Test
public void testUpdateUser() {
    // Arrange
    Long userId = 1L;
    User existingUser = new User();
    existingUser.setId(userId);

    User updatedUserDetails = new User();
    updatedUserDetails.setUsernname("newUsername");
    updatedUserDetails.setPassword("newPassword");

    when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
    when(passwordEncoder.encode(updatedUserDetails.getPassword())).thenReturn("newEncodedPassword");
    when(userRepository.save(any(User.class))).thenReturn(updatedUserDetails);

    // Act
    User updatedUser = userService.updateUser(userId, updatedUserDetails);

    // Assert
    assertEquals(updatedUserDetails.getUsername(), updatedUser.getUsername());
    assertEquals("newPassword", updatedUser.getPassword());
}

    @Test
    public void testDeleteUser() {
        // Arrange
        Long userId = 1L;
        User userToDelete = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(userToDelete));

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).delete(userToDelete);
    }

    @Test
    public void testDeleteUserNotFound() {
        // Arrange
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userService.deleteUser(userId));
    }
}
