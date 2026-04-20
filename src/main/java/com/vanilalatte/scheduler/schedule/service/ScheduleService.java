package com.vanilalatte.scheduler.schedule.service;

import com.vanilalatte.scheduler.global.exception.ScheduleNotFoundException;
import com.vanilalatte.scheduler.schedule.dto.*;
import com.vanilalatte.scheduler.schedule.entity.Schedule;
import com.vanilalatte.scheduler.schedule.repository.ScheduleRepository;
import com.vanilalatte.scheduler.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<GetSchedulePageResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "modifiedAt")
        );
        return scheduleRepository.findAllSchedulesPage(pageable);
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse getOne(Long scheduleId) {
        Schedule schedule = findScheduleById(scheduleId);
        return GetScheduleResponse.from(schedule);
    }

    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, Long loginUserId, UpdateScheduleRequest request) {
        Schedule schedule = findScheduleById(scheduleId);
        schedule.validateOwner(loginUserId);
        schedule.update(request.getTitle(), request.getContent());
        return UpdateScheduleResponse.from(schedule);
    }

    @Transactional
    public void delete(Long scheduleId, Long loginUserId) {
        Schedule schedule = findScheduleById(scheduleId);
        schedule.validateOwner(loginUserId);
        scheduleRepository.delete(schedule);
    }

    @Transactional(readOnly = true)
    public Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정입니다.")
        );
    }

}
