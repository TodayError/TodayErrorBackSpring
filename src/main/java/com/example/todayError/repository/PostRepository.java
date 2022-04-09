package com.example.todayError.repository;

import com.example.todayError.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post , Long> {
<<<<<<< HEAD
    List<Post> findByCategory(String category);
=======

>>>>>>> c17ed9a09662468b4aef72ab9aa2d502cb0b4625
}
