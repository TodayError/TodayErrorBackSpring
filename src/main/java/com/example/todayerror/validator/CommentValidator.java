package com.example.todayerror.validator;

import com.example.todayerror.dto.CommentDto;

public class CommentValidator {
    public static void validateCommentSaveRegister(CommentDto requestDto) {
        String comment = requestDto.getComment();

        if(comment.equals(null)) {
            throw new NullPointerException("댓글 내용을 작성해주세요");
        }
    }

    public static String xssChecker(CommentDto requestDto) {
        String comment = requestDto.getComment();
        if (comment.contains("script")|| comment.contains("<")||comment.contains(">")){
            comment = "Xss 안돼요 하지마세요!";
        }
        return comment;
    }
}
