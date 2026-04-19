package com.vanilalatte.scheduler.schedule.service;

import com.vanilalatte.scheduler.schedule.dto.*;
import com.vanilalatte.scheduler.schedule.entity.Schedule;
import com.vanilalatte.scheduler.schedule.repository.ScheduleRepository;
import com.vanilalatte.scheduler.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;

    @Transactional
    public CreateScheduleResponse create(Long loginUserId, CreateScheduleRequest request) {
        Schedule schedule = new Schedule(
                userService.findUserById(loginUserId),
                request.getTitle(),
                request.getContent()
        );
        Schedule savedSchedules = scheduleRepository.save(schedule);
        return CreateScheduleResponse.from(savedSchedules);
    }

    @Transactional(readOnly = true)
    public List<GetScheduleResponse> getAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<GetScheduleResponse> dtos = new ArrayList<>();
        for (Schedule schedule : schedules){
            GetScheduleResponse dto = new GetScheduleResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getUser().getId(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse getOne(Long scheduleId) {
      Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 일정입니다.")
      );
      return new GetScheduleResponse(
              schedule.getId(),
              schedule.getTitle(),
              schedule.getContent(),
              schedule.getUser().getId(),
              schedule.getCreatedAt(),
              schedule.getModifiedAt()
      );
    }

    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, Long loginUserId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 일정입니다.")
        );

        if (!schedule.getUser().getId().equals(loginUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
        }

        schedule.update(request.getTitle(), request.getContent());
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getId(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long scheduleId, Long loginUserId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 일정입니다.")
        );

        if (!schedule.getUser().getId().equals(loginUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
        }

        scheduleRepository.delete(schedule);
    }

    @Transactional(readOnly = true)
    public Schedule findScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 일정입니다.")
        );
        return schedule;
    }
}
