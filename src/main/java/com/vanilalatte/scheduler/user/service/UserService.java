package com.vanilalatte.scheduler.user.service;

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

    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        User user = new User(request.getUserName(), request.getEmail(), request.getPassword());
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> getAll() {
        List<User> users = userRepository.findAll();
        List<GetUserResponse> dtos = new ArrayList<>();
        for (User user : users) {
            GetUserResponse dto = new GetUserResponse(
                    user.getId(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    user.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public GetUserResponse getOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저 입니다.")
        );
        return new GetUserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public UpdateUserResponse update(Long userId, Long loginUserId, UpdateUserRequest request) {
        if (!userId.equals(loginUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인만 수정 가능합니다.");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저 입니다.")
        );

        user.update(request.getUserName(), request.getEmail());
        return new UpdateUserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );

    }

    @Transactional
    public void delete(Long userId, Long loginUserId) {
        if (!userId.equals(loginUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인만 삭제 가능합니다.");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저 입니다.")
        );

        userRepository.delete(user);
    }

    public User findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저 입니다.")
        );
        return user;
    }


    @Transactional
    public Long login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return user.getId();
    }
}
