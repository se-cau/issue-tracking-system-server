package se.issuetrackingsystem.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    public void register(RegisterRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();
        String role = request.getRole();

        if (isDuplicateUsername(username)) {
            throw new CustomException(ErrorCode.USER_FORBIDDEN);
        }

        User user = createUser(role, username, passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        String username = request.getUsername();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

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
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
