package com.example.todayerror.dto.PostDto;

import com.example.todayerror.domain.Post;
import com.example.todayerror.domain.User;
import lombok.*;

public class PostDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    @Getter
    public static class SaveRequest {
        private String title;
        private String content;
        private Boolean completed;
        private String category;

        public Post toEntity(){
            Post post = Post.builder()
                    .title(title)
                    .content(content)
                    .completed(completed)
                    .category(category)
                    .build();
            return post;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    @Getter
    public static class PutRequest {
        private String title;
        private String content;
        private Boolean completed;
        private String category;

        public Post toEntity(){
            Post post = Post.builder()
                    .title(title)
                    .content(content)
                    .completed(completed)
                    .category(category)
                    .build();
            return post;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    @Getter
    public static class DetailResponse {
        private String nickName;
        private String title;
        private String content;
        private String imageUrl;
        private String createdAt;
        private Boolean completed;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    @Getter
    public static class MainResponse {
        private Long postId;
        private String nickName;
        private String title;
        private String category;
        private String imageUrl;
        private Boolean completed;
        private String createdAt;

    }
}
