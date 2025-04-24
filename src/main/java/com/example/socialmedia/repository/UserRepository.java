package com.example.socialmedia.repository;

import com.example.socialmedia.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByPhoneNumber(String phoneNumber);
}
