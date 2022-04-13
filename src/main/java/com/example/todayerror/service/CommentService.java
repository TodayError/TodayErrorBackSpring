package com.example.todayerror.service;

import com.example.todayerror.domain.Comment;
import com.example.todayerror.domain.Post;
import com.example.todayerror.dto.CommentDto;
import com.example.todayerror.repository.CommentRepository;
import com.example.todayerror.repository.PostRepository;
import com.example.todayerror.security.UserDetailsImpl;
import com.example.todayerror.validator.CommentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
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
    @Transactional
    public Comment createComment(CommentDto requestDto) {
        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );
//        CommentValidator.validateCommentSaveRegister(requestDto);
//        String commentChanger = CommentValidator.xssChecker(requestDto);

        Comment comment = Comment.builder()
                .comment(requestDto.getComment())
                .post(post)
                .build();
        commentRepository.save(comment);
        return comment;
    }

    // 댓글 수정
    @Transactional
    public void update(Long id, CommentDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않습니다.")
        );
        Post post = comment.getPost();
        comment = comment.builder()
                .comment(requestDto.getComment())
                .post(post)
                .build();
        commentRepository.save(comment);
    }


    // 댓글 삭제
    public HashMap<String, Long> deleteComment(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("댓글이 존재하지 않습니다."));
        if(comment.getUser().getUsername().equals(userDetails.getUsername())){
            // 댓글 삭제 후 삭제한 댓글의 ID 리턴
            HashMap<String, Long> responseId = new HashMap<>();
            responseId.put("commentId", comment.getId());
            commentRepository.deleteById(commentId);
            return responseId;
        } else{
            throw new IllegalArgumentException("댓글 삭제에 실패하였습니다.");
        }
    }

}
