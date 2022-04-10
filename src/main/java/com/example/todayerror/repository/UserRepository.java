package com.example.todayerror.repository;

import com.example.todayerror.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String nickname);

    Optional<Object> findByKakaoId(Long kakaoId);
    Optional<User> findByUsername(String username);
}