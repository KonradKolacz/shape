package com.example.final_test.service;

import com.example.final_test.domain.User;
import com.example.final_test.exception.UserNotFoundException;
import com.example.final_test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoggedUserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        String login = getLogin();
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    public String getLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
