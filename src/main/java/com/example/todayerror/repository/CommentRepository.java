package com.example.todayerror.repository;

import com.example.todayerror.domain.Comment;
import com.example.todayerror.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostOrderByCreatedAtDesc(Long postId);
    Optional<Comment> findCommentByPostAndId(Post post, Long postId);
}