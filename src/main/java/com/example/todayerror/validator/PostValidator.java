package com.example.todayerror.validator;

import com.example.todayerror.domain.User;
import com.example.todayerror.dto.PostDto.PostDto;
import org.springframework.web.multipart.MultipartFile;

public class PostValidator {
    public static void validatePostSaveRegister(PostDto.SaveRequest postDto , MultipartFile multipartFile, User user) {
        User validUser = user;
        String nickName = user.getUsername();
        String title = postDto.getTitle();
        String content = postDto.getContent();
        String category = postDto.getCategory();
        Boolean completed = postDto.getCompleted();

        if (validUser.equals(null) || nickName.equals(null)){
            throw new NullPointerException("로그인 회원가입이 되어있지 않은 사용자입니다.");
        }

        if (title.equals(null) || content.equals(null)){
            throw new NullPointerException("게시글 제목과 내용을 확인해주세요");
        }

        if (category.equals(null) || completed.equals(null)){
            throw new NullPointerException("카테고리와 에러의 해결여부를 확인해주세요");
        }

        if (multipartFile.equals(null)){
            throw new NullPointerException("사진을 등록해주세요");
        }
    }

}
