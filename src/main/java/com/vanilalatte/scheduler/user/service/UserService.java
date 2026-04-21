package com.vanilalatte.scheduler.user.service;

import com.vanilalatte.scheduler.global.config.PasswordEncoder;
import com.vanilalatte.scheduler.global.exception.DuplicateEmailException;
import com.vanilalatte.scheduler.global.exception.UnauthorizedException;
import com.vanilalatte.scheduler.global.exception.UserNotFoundException;
import com.vanilalatte.scheduler.user.dto.*;
import com.vanilalatte.scheduler.user.entity.User;
import com.vanilalatte.scheduler.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponse create(CreateUserRequest request) {
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
        return userRepository.findAll().stream()
                .map(GetUserResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public GetUserResponse getOne(Long userId) {
        User user = getUserOrThrow(userId);
        return GetUserResponse.from(user);
    }

    @Transactional
    public UpdateUserResponse update(Long userId, Long loginUserId, UpdateUserRequest request) {
        User user = getUserOrThrow(userId);
        user.validateSameUser(loginUserId);
        validateDuplicatedEmail(request.getEmail(), userId);

        user.update(request.getUserName(), request.getEmail());
        return UpdateUserResponse.from(user);
    }

    @Transactional
    public void delete(Long userId, Long loginUserId) {
        User user = getUserOrThrow(userId);
        user.validateSameUser(loginUserId);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public User getUserOrThrow(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("존재하지 않는 유저입니다.")
        );
    }

    private void validateDuplicatedEmail(String email, Long userId) {
        userRepository.findByEmail(email)
                .ifPresent(foundUser -> {
                    if (!foundUser.getId().equals(userId)) {
                        throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
                    }
                });
    }

}
