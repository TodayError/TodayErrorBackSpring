package com.example.todayerror.dto.PostDto;


import com.example.todayerror.domain.Comment;
import com.example.todayerror.domain.User;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
public class PostDto {

    private Long Id;
    private String nickName;
    private String title;
    private String content;
    private String img;
    private List<Comment> comment;
    private User user;
    private String category;
}
