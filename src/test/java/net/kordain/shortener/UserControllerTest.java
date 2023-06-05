package net.kordain.shortener;

import net.kordian.shortener.user.UserController;
import net.kordian.shortener.user.UserDTO;
import net.kordian.shortener.user.UserService;
import net.kordian.shortener.user.UserValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testCreateUser() {
        UserDTO userDTO = new UserDTO();
        UserDTO createdUser = new UserDTO();

        when(userService.createUser(userDTO)).thenReturn(createdUser);

        ResponseEntity<UserDTO> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        ResponseEntity<UserDTO> actualResponse = userController.createUser(userDTO);

        assertEquals(expectedResponse, actualResponse);
        verify(userService, times(1)).createUser(userDTO);
    }

    @Test
    void testGetAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        users.add(new UserDTO());

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserDTO>> expectedResponse = ResponseEntity.ok(users);
        ResponseEntity<List<UserDTO>> actualResponse = userController.getAllUsers();

        assertEquals(expectedResponse, actualResponse);
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetAllUsersPaged() {
        int page = 0;
        int size = 10;
        List<UserDTO> users = new ArrayList<>();
        Page<UserDTO> userPage = new PageImpl<>(users);

        when(userService.getAllUsersPaged(any(PageRequest.class))).thenReturn(userPage);

        ResponseEntity<Page<UserDTO>> expectedResponse = ResponseEntity.ok(userPage);
        ResponseEntity<Page<UserDTO>> actualResponse = userController.getAllUsersPaged(page, size);

        assertEquals(expectedResponse, actualResponse);
        verify(userService, times(1)).getAllUsersPaged(PageRequest.of(page, size));
    }

    @Test
    void testGetUserById() {
        int id = 1;
        UserDTO user = new UserDTO();

        when(userService.getUserById(id)).thenReturn(user);

        ResponseEntity<UserDTO> expectedResponse = ResponseEntity.ok(user);
        ResponseEntity<UserDTO> actualResponse = userController.getUserById(id);

        assertEquals(expectedResponse, actualResponse);
        verify(userService, times(1)).getUserById(id);
    }

    @Test
    void testDeleteUser() {
        int id = 1;

        ResponseEntity<Void> expectedResponse = ResponseEntity.noContent().build();
        ResponseEntity<Void> actualResponse = userController.deleteUser(id);

        assertEquals(expectedResponse, actualResponse);
        verify(userService, times(1)).deleteUser(id);
    }

    @Test
    void testUpdateUser() {
        int id = 1;
        UserDTO updatedUserDTO = new UserDTO();
        UserDTO user = new UserDTO();

        when(userService.updateUser(id, updatedUserDTO)).thenReturn(user);

        ResponseEntity<UserDTO> expectedResponse = ResponseEntity.ok(user);
        ResponseEntity<UserDTO> actualResponse = userController.updateUser(id, updatedUserDTO);

        assertEquals(expectedResponse, actualResponse);
        verify(userService, times(1)).updateUser(id, updatedUserDTO);
    }

    @Test
    void testHandleUserValidationException() {
        UserValidationException ex = new UserValidationException("Validation error");

        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        ResponseEntity<String> actualResponse = userController.handleUserValidationException(ex);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testHandleResponseStatusException() {
        ResponseStatusException ex = new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");

        ResponseEntity<String> expectedResponse = ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
        ResponseEntity<String> actualResponse = userController.handleResponseStatusException(ex);

        assertEquals(expectedResponse, actualResponse);
    }
}
