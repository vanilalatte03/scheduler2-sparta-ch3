package com.vanilalatte.scheduler.schedule.repository;

import com.vanilalatte.scheduler.schedule.dto.GetSchedulePageResponse;
import com.vanilalatte.scheduler.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query(
        value = """
            select new com.vanilalatte.scheduler.schedule.dto.GetSchedulePageResponse(
                s.id,
                s.title,
                s.content,
                count(c.id),
                s.createdAt,
                s.modifiedAt,
                u.userName
            )
            from Schedule s
            join s.user u
            left join Comment c on c.schedule = s
            group by s.id, s.title, s.content, s.createdAt, s.modifiedAt, u.userName
            """,
        countQuery = """
            select count(s)
            from Schedule s
            """
    )
    Page<GetSchedulePageResponse> findAllSchedulesPage(Pageable pageable);
}
