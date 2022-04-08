package com.example.todayError.service;

import com.example.todayError.domain.Comment;
import com.example.todayError.dto.CommentDto;
import com.example.todayError.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;


    //댓글 조회
    public List<Comment> getComment(Long postId) {
        return commentRepository.findAllByPostidOrderByCreatedAtDesc(postId);
    }

    // 댓글작성
    public Comment createComment(CommentDto requestDto) {
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
