package com.vanilalatte.scheduler.schedule.repository;

import com.vanilalatte.scheduler.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
