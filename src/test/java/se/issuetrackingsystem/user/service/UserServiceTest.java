package se.issuetrackingsystem.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import se.issuetrackingsystem.common.exception.CustomException;
import se.issuetrackingsystem.common.exception.ErrorCode;
import se.issuetrackingsystem.user.domain.Admin;
import se.issuetrackingsystem.user.domain.Dev;
import se.issuetrackingsystem.user.domain.Tester;
import se.issuetrackingsystem.user.dto.LoginRequest;
import se.issuetrackingsystem.user.dto.RegisterRequest;
import se.issuetrackingsystem.user.dto.UserResponse;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void register() {
        RegisterRequest request = new RegisterRequest("TestDev","0000", "Dev");

        UserResponse response = userService.register(request);

        assertNotNull(response);
        assertEquals("TestDev", response.getUsername());
    }

    @Test
    void registerWithWrongRole() {
        RegisterRequest request = new RegisterRequest("TestDev", "0000", "WrongRole");

        CustomException exception = assertThrows(CustomException.class, () -> userService.register(request));
        assertEquals(ErrorCode.ROLE_BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    void registerWithDuplicateUsername() {
        RegisterRequest request1 = new RegisterRequest("TestDev", "0000", "Dev");
        RegisterRequest request2 = new RegisterRequest("TestDev", "0000", "Dev");

        userService.register(request1);

        CustomException exception = assertThrows(CustomException.class, () -> userService.register(request2));
        assertEquals(ErrorCode.USERNAME_CONFLICT, exception.getErrorCode());
    }

    @Test
    void login() {
        Dev user = new Dev("TestDev", passwordEncoder.encode("0000"));
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest("TestDev","0000");
        UserResponse response = userService.login(loginRequest);

        assertNotNull(response);
        assertEquals("TestDev", response.getUsername());
    }

    @Test
    void loginWithWrongPassword() {
        Dev user = new Dev("TestDev", passwordEncoder.encode("0000"));
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest("TestDev", "wrongPassword");

        CustomException exception = assertThrows(CustomException.class, () -> userService.login(loginRequest));
        assertEquals(ErrorCode.PASSWORD_BAD_REQUEST, exception.getErrorCode());
    }

    @Test
    void getUsers() {
        Dev user1 = new Dev("TestDev", passwordEncoder.encode("0000"));
        Tester user2 = new Tester("TestTester", passwordEncoder.encode("0000"));
        userRepository.save(user1);
        userRepository.save(user2);

        List<UserResponse> response = userService.getUsers();

        assertEquals(2, response.size());
        assertTrue(response.stream().anyMatch(u -> "TestDev".equals(u.getUsername())));
        assertTrue(response.stream().anyMatch(u -> "TestTester".equals(u.getUsername())));
    }

    @Test
    void getContributors() {
        Admin user1 = new Admin("TestAdmin", passwordEncoder.encode("0000"));
        Tester user2 = new Tester("TestTester", passwordEncoder.encode("0000"));
        userRepository.save(user1);
        userRepository.save(user2);

        List<UserResponse> response = userService.getContributors();

        assertEquals(1, response.size());
        assertTrue(response.stream().anyMatch(u -> "TestTester".equals(u.getUsername())));
    }
}