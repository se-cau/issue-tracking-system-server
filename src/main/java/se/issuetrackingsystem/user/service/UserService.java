package se.issuetrackingsystem.user.service;

import se.issuetrackingsystem.user.dto.LoginRequest;
import se.issuetrackingsystem.user.dto.RegisterRequest;

public interface UserService {

    void register(RegisterRequest request);

    void login(LoginRequest request);
}