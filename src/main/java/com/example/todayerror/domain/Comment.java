package com.example.todayerror.domain;

import com.example.todayerror.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Comment extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String comment;

    //회원은 여러개의 댓글을 가질 수 있기에 manytoone
    @ManyToOne
    private User user;

    public void update(CommentDto RequestDto) {
        this.comment = RequestDto.getComment();

    }
}