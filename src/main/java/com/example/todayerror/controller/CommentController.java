package com.example.todayerror.controller;

import com.example.todayerror.domain.Comment;
import com.example.todayerror.dto.CommentDto;
import com.example.todayerror.security.UserDetailsImpl;
import com.example.todayerror.security.jwt.JwtDecoder;
import com.example.todayerror.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    private final JwtDecoder jwtDecoder;
    // 댓글 작성
    @ApiOperation(value = "댓글 Post", notes = "댓글을 저장합니다.")
    @PostMapping("/comment")
    public ResponseEntity createComment(@RequestBody CommentDto requestDto,
                                        @RequestHeader("Authorization") String user
    ) {
        // 로그인 되어 있는 ID
        if (user != null) {
            String username = jwtDecoder.decodeUsername(user);
            return commentService.createComment(requestDto, username);
        }
        return new ResponseEntity("실패", HttpStatus.BAD_REQUEST);
    }


//    @ApiOperation(value = "댓글 Get", notes = "댓글을 출력합니다.")
//    @GetMapping("/comment/{postId}")
//    public List<Comment> getComment(@PathVariable Long postId) {
//        return commentService.getComment(postId);
//    }
    //수정
    @ApiOperation(value = "댓글 Get", notes = "댓글을 출력합니다.")
    @GetMapping("/comment/{postId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable Long postId) {
        return new ResponseEntity(commentService.getComment(postId), HttpStatus.OK);
    }


    // 댓글 수정
    @ApiOperation(value = "댓글 Update", notes = "댓글을 수정합니다.")
    @PutMapping("/comment/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long commentId,
                             @RequestHeader("Authorization") String user,
                              @RequestBody CommentDto requestDto
                              ) {
        String username = jwtDecoder.decodeUsername(user);
        return commentService.update(commentId, username, requestDto);
    }

    // 댓글 삭제
    @ApiOperation(value = "댓글 Delete", notes = "댓글을 삭제합니다.")
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity deleteReply(@PathVariable Long commentId,
                              @RequestHeader("Authorization") String user
    ) {
        String username = jwtDecoder.decodeUsername(user);
        return commentService.deleteComment(commentId, username);
    }
}