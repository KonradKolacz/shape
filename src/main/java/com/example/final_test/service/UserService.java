package com.example.final_test.service;

import com.example.final_test.command.UserCommand;
import com.example.final_test.domain.Role;
import com.example.final_test.domain.User;
import com.example.final_test.dto.UserDto;
import com.example.final_test.exception.NoRoleTypeException;
import com.example.final_test.exception.UserExistsException;
import com.example.final_test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UserDto add(UserCommand userCommand) {
        checkUser(userCommand);
        User user = modelMapper.map(userCommand, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findAllUsers(Pageable pageable) {
        List<UserDto> results = userRepository.findAll(pageable).stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
        return new PageImpl<>(results, pageable, userRepository.count());
    }

    private void checkUser(UserCommand user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new UserExistsException(user.getLogin());
        }
        Stream.of(Role.values())
                .filter(role -> user.getRole().equals(role.toString()))
                .findFirst()
                .orElseThrow(() -> new NoRoleTypeException(user.getRole()));
    }
}
