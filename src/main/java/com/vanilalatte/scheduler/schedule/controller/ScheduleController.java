package com.vanilalatte.scheduler.schedule.controller;

import com.vanilalatte.scheduler.global.util.SessionUtils;
import com.vanilalatte.scheduler.schedule.dto.*;
import com.vanilalatte.scheduler.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @RequestBody @Valid CreateScheduleRequest request,
            HttpSession session
    ) {
        Long loginUserId = SessionUtils.getLoginUserId(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.create(loginUserId, request));
    }
    @GetMapping
    public ResponseEntity<Page<GetSchedulePageResponse>> getSchedules(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size
    ) {
        return ResponseEntity.ok(scheduleService.getAll(page, size));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok().body(scheduleService.getOne(scheduleId));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody @Valid UpdateScheduleRequest request,
            HttpSession session
    ) {
        Long loginUserId = SessionUtils.getLoginUserId(session);
        return ResponseEntity.ok().body(scheduleService.update(scheduleId, loginUserId, request));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            HttpSession session
    ) {
        Long loginUserId = SessionUtils.getLoginUserId(session);
        scheduleService.delete(scheduleId, loginUserId);
        return ResponseEntity.noContent().build();
    }

}
