package com.vanilalatte.scheduler.user.controller;

import com.vanilalatte.scheduler.user.dto.*;
import com.vanilalatte.scheduler.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }

    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getUsers(){
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("{userId}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable Long userId){
        return ResponseEntity.ok().body(userService.getOne(userId));
    }

    @PatchMapping("{userId}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody @Valid UpdateUserRequest request,
            HttpSession session
    ){
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        return ResponseEntity.ok().body(userService.update(userId, loginUserId, request));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId,
            HttpSession session
    ) {
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        userService.delete(userId, loginUserId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request, HttpServletRequest httpRequest) {
        Long userid = userService.login(request.getEmail(), request.getPassword());
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("userId", userid);
        return ResponseEntity.ok("로그인 성공");
    }

}
