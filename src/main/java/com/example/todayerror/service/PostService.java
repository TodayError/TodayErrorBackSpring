package com.example.todayerror.service;

import com.example.todayerror.domain.Post;
import com.example.todayerror.dto.PostDto.PostDto;
import com.example.todayerror.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final AwsS3Service awsS3Service;
    private final PostRepository postRepository;

    @Transactional
    public ResponseEntity<String> save(MultipartFile multipartFile , PostDto postDto) {
        Post savePost = Post.builder()
                .nickName(postDto.getNickName())
                .title(postDto.getTitle())
                .img(awsS3Service.uploadFile(multipartFile))
                .content(postDto.getContent())
                .category(postDto.getCategory())
                .build();
                 postRepository.save(savePost);
        return new ResponseEntity(savePost.getNickName() , HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity getAllPost() {
        List<Post> allPost = postRepository.findAll();
        return new ResponseEntity(allPost , HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> getCategoryPost(String category) {
        List<Post> getCategoryPost = postRepository.findByCategory(category);
        return new ResponseEntity(getCategoryPost , HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Post> getPostDeatils(Long postId){
        Post getDetailPost = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("찾으시는 게시물이 존재하지 않습니다.")
        );
        return new ResponseEntity(getDetailPost , HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> update(Long postId , PostDto postDto) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("찾으시는 게시글이 존재하지 않습니다.")
        );
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCategory(postDto.getCategory());
        postRepository.save(post);

        return new ResponseEntity(post , HttpStatus.OK);
    }

    public ResponseEntity<String> delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("찾으시는 게시글이 존재하지 않습니다.")
        );
        postRepository.delete(post);
        awsS3Service.deleteFile(post.getImg());
        return new ResponseEntity("삭제완료" , HttpStatus.OK);
    }
}
