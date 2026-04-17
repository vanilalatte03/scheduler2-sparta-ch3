package com.vanilalatte.scheduler.schedule.service;

import com.vanilalatte.scheduler.schedule.dto.*;
import com.vanilalatte.scheduler.schedule.entity.Schedule;
import com.vanilalatte.scheduler.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateScheduleResponse create(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), request.getUserName());
        Schedule savedSchedules = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                savedSchedules.getId(),
                savedSchedules.getTitle(),
                savedSchedules.getContent(),
                savedSchedules.getUserName(),
                savedSchedules.getCreatedAt(),
                savedSchedules.getModifiedAt()
        );
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
                    schedule.getUserName(),
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
              () -> new IllegalStateException("없는 일정 입니다.")
      );
      return new GetScheduleResponse(
              schedule.getId(),
              schedule.getTitle(),
              schedule.getContent(),
              schedule.getUserName(),
              schedule.getCreatedAt(),
              schedule.getModifiedAt()
      );
    }

    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정 입니다.")
        );

        schedule.update(request.getTitle(), request.getContent());
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUserName(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long scheduleId) {
        boolean exist = scheduleRepository.existsById(scheduleId);
        if (!exist) {
            throw new IllegalStateException("없는 일정 입니다.");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
