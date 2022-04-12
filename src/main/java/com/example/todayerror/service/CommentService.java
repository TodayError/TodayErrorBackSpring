package com.example.todayerror.service;

import com.example.todayerror.domain.Comment;
import com.example.todayerror.domain.Post;
import com.example.todayerror.dto.CommentDto;
import com.example.todayerror.repository.CommentRepository;
import com.example.todayerror.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    //댓글 조회
    public List<Comment> getComment(Long postId) {
        return commentRepository.findAllByPostOrderByCreatedAtDesc(postId);
    }

    // 댓글작성
    public Comment createComment(CommentDto requestDto) {
        Optional<Post> foundPost = postRepository.findById(requestDto.getPostId());

        Post post = foundPost.get();

        String commentCheck = requestDto.getComment();
        if (commentCheck.contains("script")|| commentCheck.contains("<")||commentCheck.contains(">")){
//            Comment comment = new Comment(requestDto, userId,"xss 안돼요,, 하지마세요ㅠㅠ");
            Comment comment = new Comment(requestDto, "xss 안돼요,, 하지마세요ㅠㅠ");
            commentRepository.save(comment);
            return comment;
        }
//        Comment comment = new Comment(requestDto, userId);
        Comment comment = new Comment(requestDto);
        commentRepository.save(comment);
        return comment;
    }

    // 댓글 수정
    @Transactional
    public void update(Long id, CommentDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않습니다.")
        );
        comment.update(requestDto);
    }
}