package com.vanilalatte.scheduler.schedule.entity;

import com.vanilalatte.scheduler.global.entity.BaseEntity;
import com.vanilalatte.scheduler.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일정 도메인을 표현하는 엔티티다.
 */
@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 200)
    private String content;

    /**
     * 일정 엔티티를 생성한다.
     *
     * @param title 할일 제목
     * @param content 할일 내용
     */
    public Schedule(User user, String title, String content){
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content= content;
    }
}
