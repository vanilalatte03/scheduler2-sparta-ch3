package com.vanilalatte.scheduler.user.repository;

import com.vanilalatte.scheduler.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
