package com.vanilalatte.scheduler.comment.entity;

import com.vanilalatte.scheduler.global.entity.BaseEntity;
import com.vanilalatte.scheduler.global.exception.ForbiddenException;
import com.vanilalatte.scheduler.schedule.entity.Schedule;
import com.vanilalatte.scheduler.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일정에 작성된 댓글 정보를 나타내는 엔티티입니다.
 */
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

    /**
     * 댓글 작성자가 현재 로그인 사용자와 같은지 검증합니다.
     *
     * @param loginUserId 로그인한 사용자 ID
     * @throws ForbiddenException 작성자가 아니면 발생
     */
    public void validateOwner(Long loginUserId) {
        if (!user.getId().equals(loginUserId)) {
            throw new ForbiddenException("해당 권한이 없습니다.");
        }
    }
}
