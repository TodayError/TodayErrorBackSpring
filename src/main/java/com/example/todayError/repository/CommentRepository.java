package com.example.todayError.repository;

import com.example.todayError.domain.Comment;
import com.example.todayError.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostidOrderByCreatedAtDesc(Long postId);
    Optional<Comment> findCommentById(Post post, Long postId);
}
