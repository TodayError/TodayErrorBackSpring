package com.example.todayerror.controller;

import com.example.todayerror.domain.Comment;
import com.example.todayerror.dto.CommentDto;
import com.example.todayerror.security.UserDetailsImpl;
import com.example.todayerror.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @ApiOperation(value = "댓글 Post", notes = "댓글을 저장합니다.")
    @PostMapping("/comment")
    public boolean createComment(@RequestBody CommentDto requestDto
//                                  ,@AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
// 권한설정 완료되면 수정
//        // 로그인 되어 있는 ID
//        if (userDetails != null) {
//            Long userId = userDetails.getUser().getId();
//            commentService.commentService(requestDto, userId);
//            return true;
//        }
//        return false;
//    }
        commentService.createComment(requestDto);
        return true;
    }

    @ApiOperation(value = "댓글 Get", notes = "댓글을 출력합니다.")
    @GetMapping("/comment/{postId}")
    public List<Comment> getComment(@PathVariable Long postId
//                              @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return commentService.getComment(postId);
    }

    // 댓글 수정
    @ApiOperation(value = "댓글 Update", notes = "댓글을 수정합니다.")
    @PutMapping("/comment/{commentId}")
    public Long updateComment(@PathVariable Long commentId,
//                              @AuthenticationPrincipal UserDetailsImpl userDetails,
                              @RequestBody CommentDto requestDto
                              ) {
        commentService.update(commentId, requestDto);
        return commentId;
    }

    // 댓글 삭제
    @ApiOperation(value = "댓글 Delete", notes = "댓글을 삭제합니다.")
    @DeleteMapping("/comment/{commentId}")
    public Long deleteReply(@PathVariable Long commentId
                              ,@AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.deleteComment(commentId, userDetails);
        return commentId;
    }
}