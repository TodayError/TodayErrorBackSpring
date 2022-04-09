package com.example.todayError.dto.PostDto;


import com.example.todayError.domain.Comment;
import com.example.todayError.domain.User;
import lombok.*;

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
