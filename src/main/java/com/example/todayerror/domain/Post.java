package com.example.todayerror.domain;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @Column
    private String imageUrl;

    @Column
    private String category;

    @Column(columnDefinition = "boolean default false")
    private Boolean completed;

    //게시글은 여러개의 댓글을 가질 수 있기에 onetomany
    @OneToMany
    private List<Comment> comment;

    //회원은 여러개의 게시글을 가질 수 있기에 manytoone
    @ManyToOne
    private User user;
}