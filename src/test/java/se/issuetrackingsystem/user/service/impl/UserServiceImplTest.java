package se.issuetrackingsystem.user.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import se.issuetrackingsystem.common.exception.CustomException;
import se.issuetrackingsystem.common.exception.ErrorCode;
import se.issuetrackingsystem.user.dto.LoginRequest;
import se.issuetrackingsystem.user.dto.RegisterRequest;
import se.issuetrackingsystem.user.dto.UserResponse;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @Test
    void register() {
        RegisterRequest request = new RegisterRequest("TestDev", "0000", "Dev");

        UserResponse response = userService.register(request);

        assertNotNull(response);
        assertEquals("TestDev", response.getUsername());
    }

    @Test
    void registerWithWrongRole() {
        RegisterRequest request = new RegisterRequest("TestDev", "0000", "WrongRole");

        CustomException exception = assertThrows(CustomException.class, () -> userService.register(request));
        assertEquals(ErrorCode.ROLE_FORBIDDEN, exception.getErrorCode());
    }

    @Test
    void registerWithDuplicateUsername() {
        RegisterRequest request1 = new RegisterRequest("TestDev", "0000", "Dev");
        RegisterRequest request2 = new RegisterRequest("TestDev", "0000", "Dev");

        userService.register(request1);

        CustomException exception = assertThrows(CustomException.class, () -> userService.register(request2));
        assertEquals(ErrorCode.USERNAME_FORBIDDEN, exception.getErrorCode());
    }

    @Test
    void login() {
        RegisterRequest registerRequest = new RegisterRequest("TestDev", "0000", "Dev");
        userService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest("TestDev", "0000");
        UserResponse response = userService.login(loginRequest);

        assertNotNull(response);
        assertEquals("TestDev", response.getUsername());
    }

    @Test
    void loginWithWrongPassword() {
        RegisterRequest registerRequest = new RegisterRequest("TestDev", "0000", "Dev");
        userService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest("TestDev", "wrongpassword");

        CustomException exception = assertThrows(CustomException.class, () -> userService.login(loginRequest));
        assertEquals(ErrorCode.PASSWORD_FORBIDDEN, exception.getErrorCode());
    }

    @Test
    void getUsers() {
        RegisterRequest request1 = new RegisterRequest("TestDev", "password123", "Dev");
        RegisterRequest request2 = new RegisterRequest("TestTester", "password123", "Tester");

        userService.register(request1);
        userService.register(request2);

        List<UserResponse> users = userService.getUsers();

        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> "TestDev".equals(u.getUsername())));
        assertTrue(users.stream().anyMatch(u -> "TestTester".equals(u.getUsername())));
    }
}