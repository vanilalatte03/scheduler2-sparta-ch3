package com.vanilalatte.scheduler.user.entity;

import com.vanilalatte.scheduler.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String email;

    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public void update(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
}
