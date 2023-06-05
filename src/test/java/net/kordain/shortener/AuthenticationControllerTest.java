package net.kordain.shortener;

import net.kordian.shortener.auth.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authService;

    @InjectMocks
    private AuthenticationController authController;

    @Test
    void testRegister() {
        RegisterRequest request = new RegisterRequest();
        AuthenticationResponse response = new AuthenticationResponse();

        when(authService.register(request)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> expectedResponse = ResponseEntity.ok(response);
        ResponseEntity<AuthenticationResponse> actualResponse = authController.register(request);

        assertEquals(expectedResponse, actualResponse);
        verify(authService, times(1)).register(request);
    }

    @Test
    void testAuthenticate() {
        AuthenticationRequest request = new AuthenticationRequest();
        AuthenticationResponse response = new AuthenticationResponse();

        when(authService.authenticate(request)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> expectedResponse = ResponseEntity.ok(response);
        ResponseEntity<AuthenticationResponse> actualResponse = authController.authenticate(request);

        assertEquals(expectedResponse, actualResponse);
        verify(authService, times(1)).authenticate(request);
    }
}
