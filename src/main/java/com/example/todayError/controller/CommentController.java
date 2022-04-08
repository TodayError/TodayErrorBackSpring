package com.example.todayError.controller;

import com.example.todayError.dto.CommentDto;
import com.example.todayError.repository.CommentRepository;
import com.example.todayError.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/comment")
    public boolean createComment(@RequestBody CommentDto requestDto) {
// 권한설정 완료되면 수정
//    public boolean creatComment(@RequestBody CommentDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        // 로그인 되어 있는 ID
//        if (userDetails != null) {
//            Long userId = userDetails.getUser().getId();
//            ReplyService.createReply(requestDto, userId);
//            return true;
//        }
//        return false;
//    }
        commentService.createComment(requestDto);
        return true;
    }

    // 댓글 수정
    @PutMapping("/comment/{commentId}")
    public Long updateComment(@PathVariable Long commentId, @RequestBody CommentDto requestDto) {
        commentService.update(commentId, requestDto);
        return commentId;
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public Long deleteReply(@PathVariable Long commentId) {
        commentRepository.deleteById(commentId);
        return commentId;
    }
}
