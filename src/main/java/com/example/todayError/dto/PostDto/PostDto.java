package com.example.todayError.dto.PostDto;


import com.example.todayError.domain.Comment;
import com.example.todayError.domain.User;
<<<<<<< HEAD:src/main/java/com/example/todayError/dto/PostDto/PostDto.java
import lombok.*;
=======
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
>>>>>>> c17ed9a09662468b4aef72ab9aa2d502cb0b4625:src/main/java/com/example/todayError/dto/PostDto.java

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
public class PostDto {
<<<<<<< HEAD:src/main/java/com/example/todayError/dto/PostDto/PostDto.java
=======

>>>>>>> edd3d7330911f2cb8b39450bbe7b19a3ee8f8162:src/main/java/com/example/todayError/dto/PostDto.java
    private Long Id;
    private String nickName;
    private String title;
    private String content;
    private String img;
    private List<Comment> comment;
    private User user;
    private String category;
}
