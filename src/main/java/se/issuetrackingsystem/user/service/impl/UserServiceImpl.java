package se.issuetrackingsystem.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.issuetrackingsystem.exception.CustomException;
import se.issuetrackingsystem.exception.ErrorCode;
import se.issuetrackingsystem.user.domain.*;
import se.issuetrackingsystem.user.dto.LoginRequest;
import se.issuetrackingsystem.user.dto.RegisterRequest;
import se.issuetrackingsystem.user.dto.LoginResponse;
import se.issuetrackingsystem.user.repository.UserRepository;
import se.issuetrackingsystem.user.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public void register(RegisterRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();
        String role = request.getRole();

        if (isDuplicateUsername(username)) {
            throw new CustomException(ErrorCode.USERNAME_FORBIDDEN);
        }

        User user = createUser(role, username, passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {

        String username = request.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_FORBIDDEN);
        }

        return new LoginResponse(user);
    }

    private boolean isDuplicateUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    private User createUser(String role, String username, String password) {
        switch (role) {
            case "Admin":
                return new Admin(username, password);
            case "PL":
                return new PL(username, password);
            case "Dev":
                return new Dev(username, password);
            case "Tester":
                return new Tester(username, password);
            default:
                throw new CustomException(ErrorCode.ROLE_FORBIDDEN);
        }
    }
}
