package se.issuetrackingsystem.user.service;

import se.issuetrackingsystem.user.dto.LoginRequest;
import se.issuetrackingsystem.user.dto.RegisterRequest;
import se.issuetrackingsystem.user.dto.LoginResponse;

public interface UserService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}