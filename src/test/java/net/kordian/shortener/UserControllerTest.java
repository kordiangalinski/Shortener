package net.kordian.shortener;

import net.kordian.shortener.controllers.UserController;
import net.kordian.shortener.entities.User;
import net.kordian.shortener.exceptions.UserNotFoundException;
import net.kordian.shortener.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Mocking the UserService to return a list of users
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);
        when(userService.getAllUsers()).thenReturn(users);

        // Calling the getAllUsers() method of UserController
        ResponseEntity<List<User>> response = userController.getAllUsers();

        // Verifying the response status code and the returned users
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());

        // Verifying that the getAllUsers() method of UserService was called once
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById_UserFound() {
        // Mocking the UserService to return a user with ID 1
        User user = new User();
        user.setId(1L);
        when(userService.getUserById(1L)).thenReturn(user);

        // Calling the getUserById() method of UserController
        ResponseEntity<User> response = userController.getUserById(1L);

        // Verifying the response status code and the returned user
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());

        // Verifying that the getUserById() method of UserService was called once with the correct ID
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testGetUserById_UserNotFound() {
        // Mocking the UserService to return null (user not found)
        when(userService.getUserById(1L)).thenReturn(null);

        // Calling the getUserById() method of UserController
        ResponseEntity<User> response = userController.getUserById(1L);

        // Verifying the response status code (404 NOT FOUND)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verifying that the getUserById() method of UserService was called once with the correct ID
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testCreateUser() {
        // Mocking the UserService to create a new user
        User user = new User();
        when(userService.createUser(user)).thenReturn(user);

        // Calling the createUser() method of UserController
        ResponseEntity<User> response = userController.createUser(user);

        // Verifying the response status code and the returned user
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());

        // Verifying that the createUser() method of UserService was called once with the correct user object
        verify(userService, times(1)).createUser(user);
    }

    @Test
    void testUpdateUser_UserFound() {
        // Mocking the UserService to update an existing user
        User user = new User();
        user.setId(1L);
        when(userService.updateUser(user)).thenReturn(user);

        // Calling the updateUser() method of UserController with the ID parameter
        ResponseEntity<User> response = userController.updateUser(1L, user);

        // Verifying the response status code and the returned user
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());

        // Verifying that the updateUser() method of UserService was called once with the correct user object
        verify(userService, times(1)).updateUser(user);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        // Mocking the UserService to return null (user not found) while updating
        User user = new User();
        user.setId(1L);
        when(userService.updateUser(user)).thenReturn(null);

        // Calling the updateUser() method of UserController with the ID parameter
        ResponseEntity<User> response = userController.updateUser(1L, user);

        // Verifying the response status code (404 NOT FOUND)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verifying that the updateUser() method of UserService was called once with the correct user object
        verify(userService, times(1)).updateUser(user);
    }

    @Test
    void testDeleteUser_UserFound() {
        // Mocking the UserService to delete an existing user
        User user = new User();
        user.setId(1L);

        // Calling the deleteUser() method of UserController
        ResponseEntity<Void> response = userController.deleteUser(1L);

        // Verifying the response status code (204 NO CONTENT)
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verifying that the deleteUser() method of UserService was called once with the correct user ID
        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        // Mocking the UserService to throw UserNotFoundException when deleting
        doThrow(UserNotFoundException.class).when(userService).deleteUser(1L);

        // Calling the deleteUser() method of UserController
        ResponseEntity<Void> response = userController.deleteUser(1L);

        // Verifying the response status code (404 NOT FOUND)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verifying that the deleteUser() method of UserService was called once with the correct user ID
        verify(userService, times(1)).deleteUser(1L);
    }

    // Add more test cases as per your requirements

}
