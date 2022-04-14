package com.example.todayerror.service;

import com.example.todayerror.domain.Comment;
import com.example.todayerror.domain.Post;
import com.example.todayerror.domain.User;
import com.example.todayerror.dto.CommentDto;
import com.example.todayerror.repository.CommentRepository;
import com.example.todayerror.repository.PostRepository;
import com.example.todayerror.repository.UserRepository;
import com.example.todayerror.validator.CommentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //댓글 조회
//    public List<Comment> getComment(Long postId) {
//
//        return commentRepository.findAllByPostOrderByCreatedAtDesc(postId);
//    }
    //수정로직
    public ResponseEntity<CommentDto> getComment(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("게시글 존재 x")
        );

        List<Comment> getPostidComment = commentRepository.findByPost(post);
        List<CommentDto.Response> postIdResponse = new ArrayList<>();
        for(Comment comment : getPostidComment) {
            CommentDto.Response commentDto = CommentDto.Response.builder()
                    .commentId(comment.getId())
                    .comment(comment.getComment())
                    .userName(comment.getUser().getUsername())
                    .createdAt(commentFormmater(comment.getCreatedAt()))
                    .build();
            postIdResponse.add(commentDto);
        }
        return new ResponseEntity(postIdResponse, HttpStatus.OK);
    }

    // 댓글작성
    @Transactional
    public ResponseEntity createComment(CommentDto requestDto, String username) {
        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );
        User joinUser = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("유효한 회원을 찾을 수 없습니다.")
        );
        CommentValidator.validateCommentSaveRegister(requestDto);
//        String commentChanger = CommentValidator.xssChecker(requestDto);

        Comment comment = Comment.builder()
                .comment(requestDto.getComment())
                .post(post)
                .user(joinUser)
                .build();
        commentRepository.save(comment);

        CommentDto.commentResponse commentDto = CommentDto.commentResponse.builder()
                .commentId(comment.getId())
                .createdAt(commentFormmater(comment.getCreatedAt()))
                .build();

        return ResponseEntity.ok().body(commentDto);
    }

    // 댓글 수정
    @Transactional
    public ResponseEntity update(Long id, String username, CommentDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않습니다.")
        );
        if(!username.equals(comment.getUser().getUsername())) {
            throw  new IllegalArgumentException("유효한 회원을 찾을 수 없습니다.");
        }

        comment.update(requestDto);
        CommentDto.commentUpdateResponse commentDto = CommentDto.commentUpdateResponse.builder()
                .commentId(comment.getId())
                .modifiedAt(commentFormmater(comment.getModifiedAt()))
                .build();
        return ResponseEntity.ok().body(commentDto);
    }


    // 댓글 삭제
    @Transactional
    public ResponseEntity<String> deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("댓글이 존재하지 않습니다."));
        User findUser = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("유효한 회원을 찾을 수 없습니다.")
        );
        validateCheckUser(findUser, comment);
        if(comment.getUser().getUsername().equals(username)){
            // 댓글 삭제 후 삭제한 댓글의 ID 리턴
            HashMap<String, Long> responseId = new HashMap<>();
            responseId.put("commentId", comment.getId());
            commentRepository.deleteById(commentId);
            return new ResponseEntity("success", HttpStatus.OK);
        } else{
            throw new IllegalArgumentException("댓글 삭제에 실패하였습니다.");
        }
    }

    public String commentFormmater(LocalDateTime localDateTime){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDateTime);
    }

    private void validateCheckUser(User user, Comment comment) {
        if (!user.equals(comment.getUser())){
            throw new IllegalArgumentException("권한이 없는 유저 입니다.");
        }
    }
}
