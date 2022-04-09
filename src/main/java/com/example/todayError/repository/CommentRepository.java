package com.example.todayError.repository;

import com.example.todayError.domain.Comment;
import com.example.todayError.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
<<<<<<< HEAD
    List<Comment> findAllByPostidOrderByCreatedAtDesc(Long postId);
    Optional<Comment> findCommentById(Post post, Long postId);
=======
    List<Comment> findAllByPostOrderByCreatedAtDesc(Long postId);
    Optional<Comment> findCommentByPostAndId(Post post, Long postId);
>>>>>>> 48eae21d099f842fbd1ad4af8c1a8d0f6b4da642
}
