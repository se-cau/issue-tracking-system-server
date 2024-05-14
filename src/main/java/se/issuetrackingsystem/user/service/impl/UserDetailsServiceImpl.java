package se.issuetrackingsystem.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.issuetrackingsystem.exception.CustomException;
import se.issuetrackingsystem.exception.ErrorCode;
import se.issuetrackingsystem.user.domain.User;
import se.issuetrackingsystem.user.repository.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
        // 리턴된 User 객체의 비밀번호가 사용자로부터 입력받은 비밀번호와 일치하는지를 검사하는 기능을 포함한다.
    }
}