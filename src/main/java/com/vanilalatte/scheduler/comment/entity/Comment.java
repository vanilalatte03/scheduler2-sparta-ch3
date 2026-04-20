package com.vanilalatte.scheduler.comment.entity;

import com.vanilalatte.scheduler.global.entity.BaseEntity;
import com.vanilalatte.scheduler.schedule.entity.Schedule;
import com.vanilalatte.scheduler.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public Comment(Schedule schedule, User user, String content) {
        this.schedule = schedule;
        this.user = user;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
