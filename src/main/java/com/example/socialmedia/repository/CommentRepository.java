package com.example.socialmedia.repository;

import com.example.socialmedia.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
