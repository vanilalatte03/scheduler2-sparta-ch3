package com.vanilalatte.scheduler.schedule.entity;

import com.vanilalatte.scheduler.global.entity.BaseEntity;
import com.vanilalatte.scheduler.global.exception.ForbiddenException;
import com.vanilalatte.scheduler.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일정 도메인을 표현하는 엔티티입니다.
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
     * 일정을 생성합니다.
     *
     * @param user 작성자
     * @param title 일정 제목
     * @param content 일정 내용
     */
    public Schedule(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * 로그인한 사용자가 현재 일정의 작성자인지 검증합니다.
     *
     * @param loginUserId 로그인한 사용자 ID
     * @throws ForbiddenException 작성자가 아니면 발생
     */
    public void validateOwner(Long loginUserId) {
        if (!this.user.getId().equals(loginUserId)) {
            throw new ForbiddenException("해당 권한이 없습니다.");
        }
    }
}
