package se.issuetrackingsystem.user.service;

import se.issuetrackingsystem.user.dto.LoginRequest;
import se.issuetrackingsystem.user.dto.RegisterRequest;
import se.issuetrackingsystem.user.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse register(RegisterRequest request);

    UserResponse login(LoginRequest request);

    List<UserResponse> getUsers();
}