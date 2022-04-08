package com.example.todayError.dto;


import com.example.todayError.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {


    private Long Id;
    private String nickName;
    private String title;
    private String content;
    private List<Comment> comment;
    private User user;
    private Category category;
}
