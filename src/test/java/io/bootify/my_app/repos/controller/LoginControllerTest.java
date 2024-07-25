package io.bootify.my_app.repos.controller;

import io.bootify.my_app.controller.LoginController;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.payloads.JwtAuthRequest;
import io.bootify.my_app.payloads.JwtAuthResponse;
import io.bootify.my_app.security.CustomUserDetailsService;
import io.bootify.my_app.security.JwtTokenHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private JwtTokenHelper jwtTokenHelper;

    /*@Test
    void testGenerateToken_HappyPath() throws Exception {
        // Given
        JwtAuthRequest jwtRequest = new JwtAuthRequest("username", "password");
      //  UserDetails userDetails = new UserDetails("username", "password", true, true, true, true) ;
        User user = new User("username", "password", true);
        String token = "generated-token";
        when(customUserDetailsService.loadUserByUsername(jwtRequest.getUsername())).thenReturn(userDetails);
        when(customUserDetailsService.loadUserByUsername(jwtRequest.getUsername())).thenReturn(user);
       // when(jwtTokenHelper.generateToken(userDetails, user)).thenReturn(token);

        // When
        ResponseEntity<JwtAuthResponse> response = loginController.generateToken(jwtRequest);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new JwtAuthResponse(token, "success"), response.getBody());
    }
*/
}
