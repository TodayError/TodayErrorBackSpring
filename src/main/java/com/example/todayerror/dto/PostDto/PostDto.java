package com.example.todayerror.dto.PostDto;

import com.example.todayerror.domain.Comment;
import com.example.todayerror.domain.Post;
import com.example.todayerror.domain.User;
import lombok.*;

import java.util.List;

public class PostDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    @Getter
    public static class SaveRequest {
        private User user;
        private String nickName;
        private String title;
        private String content;
        private String imageUrl;

        private String imageName;
        private Boolean completed;
        private String category;

        public Post toEntity(){
            Post post = Post.builder()
                    .user(user)
                    .nickName(nickName)
                    .title(title)
                    .content(content)
                    .imageUrl(imageUrl)
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
        private String imageUrl;
        private String imageName;
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
        private List<Comment> comment;

        public DetailResponse(Post post) {
            this.nickName = post.getNickName();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.createdAt = String.valueOf(post.getCreatedAt());
            this.imageUrl = post.getImageUrl();
            this.completed = post.getCompleted();
            this.comment = post.getComment();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    @Getter
    public static class MainResponse {
        private String nickName;
        private String title;
        private String category;
        private String imageUrl;
        private Boolean completed;
        private String createdAt;

        public MainResponse(Post post) {
            this.nickName = post.getNickName();
            this.title = post.getTitle();
            this.category = post.getCategory();
            this.imageUrl = post.getImageUrl();
            this.completed = post.getCompleted();
            this.createdAt = String.valueOf(post.getCreatedAt());
        }
    }
}
