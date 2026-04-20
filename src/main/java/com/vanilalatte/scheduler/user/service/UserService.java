package com.vanilalatte.scheduler.user.service;

import com.vanilalatte.scheduler.global.config.PasswordEncoder;
import com.vanilalatte.scheduler.user.dto.*;
import com.vanilalatte.scheduler.user.entity.User;
import com.vanilalatte.scheduler.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        String rawPassword = request.getPassword();

        String encodedPassword = passwordEncoder.encode(rawPassword);

        User user = new User(request.getUserName(), request.getEmail(), encodedPassword);
        User savedUser = userRepository.save(user);
        return CreateUserResponse.from(savedUser);

    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> getAll() {
        List<User> users = userRepository.findAll();
        List<GetUserResponse> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(GetUserResponse.from(user));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public GetUserResponse getOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.")
        );
        return GetUserResponse.from(user);
    }

    @Transactional
    public UpdateUserResponse update(Long userId, Long loginUserId, UpdateUserRequest request) {
        if (!userId.equals(loginUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인만 수정 가능합니다.");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.")
        );

        user.update(request.getUserName(), request.getEmail());
        return UpdateUserResponse.from(user);
    }

    @Transactional
    public void delete(Long userId, Long loginUserId) {
        if (!userId.equals(loginUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인만 삭제 가능합니다.");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.")
        );

        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public User findUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.")
        );
        return user;
    }

    @Transactional(readOnly = true)
    public Long login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return user.getId();
    }
}
