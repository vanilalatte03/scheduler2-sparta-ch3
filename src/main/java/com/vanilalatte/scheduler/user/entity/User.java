package com.vanilalatte.scheduler.user.entity;

import com.vanilalatte.scheduler.global.entity.BaseEntity;
import com.vanilalatte.scheduler.global.exception.ForbiddenException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 정보를 나타내는 엔티티입니다.
 */
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String userName;

    @Column(unique = true, nullable = false, length = 60)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void update(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    /**
     * 로그인한 사용자가 현재 사용자 본인인지 검증합니다.
     *
     * @param loginUserId 로그인한 사용자 ID
     * @throws ForbiddenException 본인이 아니면 발생
     */
    public void validateSameUser(Long loginUserId) {
        if (!this.id.equals(loginUserId)) {
            throw new ForbiddenException("권한이 없습니다.");
        }
    }
}
