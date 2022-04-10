package com.example.todayerror.dto.PostDto;
import com.example.todayerror.domain.Comment;
import com.example.todayerror.domain.Post;
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
    private String imageUrl;
    private Boolean completed;
    private String category;
    private List<Comment> comment;
    private String createdAt;
    private User user;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    @Getter
    public static class MainResponse {
        private String nickName;
        private String title;
        private String category;
        private String createdAt;
        private String imageUrl;
        private Boolean completed;

        public MainResponse(Post post) {
            this.nickName = post.getNickName();
            this.title = post.getTitle();
            this.category = post.getCategory();
            this.createdAt = String.valueOf(post.getCreatedAt());
            this.imageUrl = post.getImageUrl();
            this.completed = post.getCompleted();
        }
    }
}
