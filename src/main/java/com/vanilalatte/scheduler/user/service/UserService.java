package com.vanilalatte.scheduler.user.service;

import com.vanilalatte.scheduler.global.config.PasswordEncoder;
import com.vanilalatte.scheduler.global.exception.DuplicateEmailException;
import com.vanilalatte.scheduler.global.exception.ForbiddenException;
import com.vanilalatte.scheduler.global.exception.UnauthorizedException;
import com.vanilalatte.scheduler.global.exception.UserNotFoundException;
import com.vanilalatte.scheduler.user.dto.*;
import com.vanilalatte.scheduler.user.entity.User;
import com.vanilalatte.scheduler.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
                });

        String encodedPassword = passwordEncoder.encode(request.getPassword());

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
        User user = findUserById(userId);
        return GetUserResponse.from(user);
    }

    @Transactional
    public UpdateUserResponse update(Long userId, Long loginUserId, UpdateUserRequest request) {
        if (!userId.equals(loginUserId)) {
            throw new ForbiddenException("본인만 수정 가능합니다.");
        }

        User user = findUserById(userId);

        userRepository.findByEmail(request.getEmail())
                .ifPresent(foundUser -> {
                    if (!foundUser.getId().equals(userId)) {
                        throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
                    }
                });

        user.update(request.getUserName(), request.getEmail());
        return UpdateUserResponse.from(user);
    }

    @Transactional
    public void delete(Long userId, Long loginUserId) {
        if (!userId.equals(loginUserId)) {
            throw new ForbiddenException("본인만 삭제 가능합니다.");
        }

        User user = findUserById(userId);

        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("존재하지 않는 유저입니다.")
        );
    }

    @Transactional(readOnly = true)
    public Long login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return user.getId();
    }
}
