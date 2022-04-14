package com.example.todayerror.domain;


import com.example.todayerror.dto.PostDto.PostDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    @Column
    private String nickName;

    @Column
    private String title;

    @Column
    private String content;

    @Column(columnDefinition = "boolean default false")
    private Boolean completed;

    @Column
    private String category;

    @Column
    private String imageUrl;

    @Column
    private String imageFilename;


    //게시글은 여러개의 댓글을 가질 수 있기에 onetomany
    @OneToMany
    @JsonIgnoreProperties
    private List<Comment> comment;

    //회원은 여러개의 게시글을 가질 수 있기에 manytoone
    @ManyToOne
    private User user;

    public void update(PostDto.PutRequest putRequest , String imageUrl , String imageFilename) {
        this.title = putRequest.getTitle();
        this.content = putRequest.getContent();
        this.completed = putRequest.getCompleted();
        this.category = putRequest.getCategory();
        this.imageUrl = imageUrl;
        this.imageFilename = imageFilename;
    }
}