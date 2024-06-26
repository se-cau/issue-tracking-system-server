package se.issuetrackingsystem.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.issuetrackingsystem.common.exception.CustomException;
import se.issuetrackingsystem.common.exception.ErrorCode;
import se.issuetrackingsystem.user.domain.*;
import se.issuetrackingsystem.user.dto.LoginRequest;
import se.issuetrackingsystem.user.dto.RegisterRequest;
import se.issuetrackingsystem.user.dto.UserResponse;
import se.issuetrackingsystem.projectContributor.repository.ProjectContributorRepository;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public UserResponse register(RegisterRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();
        String role = request.getRole();

        if (isDuplicateUsername(username)) {
            throw new CustomException(ErrorCode.USERNAME_CONFLICT);
        }

        User user = createUser(role, username, passwordEncoder.encode(password));
        userRepository.save(user);

        return new UserResponse(user);
    }

    @Transactional
    public UserResponse login(LoginRequest request) {

        String username = request.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_BAD_REQUEST);
        }

        return new UserResponse(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {

        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(UserResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getContributors() {

        List<User> users = userRepository.findContributors()
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return users
                .stream()
                .map(UserResponse::new)
                .toList();
    }

    private boolean isDuplicateUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    private User createUser(String role, String username, String password) {
        return switch (role) {
            case "Admin" -> new Admin(username, password);
            case "PL" -> new PL(username, password);
            case "Dev" -> new Dev(username, password);
            case "Tester" -> new Tester(username, password);
            default -> throw new CustomException(ErrorCode.ROLE_BAD_REQUEST);
        };
    }
}
