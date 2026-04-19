package com.vanilalatte.scheduler.schedule.controller;

import com.vanilalatte.scheduler.schedule.dto.*;
import com.vanilalatte.scheduler.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @RequestBody CreateScheduleRequest request,
            HttpSession session
    ) {
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.create(loginUserId, request));
    }

    @GetMapping
    public ResponseEntity<List<GetScheduleResponse>> getSchedules() {
        return ResponseEntity.ok().body(scheduleService.getAll());
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok().body(scheduleService.getOne(scheduleId));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request,
            HttpSession session
    ) {
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        return ResponseEntity.ok().body(scheduleService.update(scheduleId, loginUserId, request));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            HttpSession session
    ) {
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        scheduleService.delete(scheduleId, loginUserId);
        return ResponseEntity.noContent().build();
    }

}
