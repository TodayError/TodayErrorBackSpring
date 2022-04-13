package com.example.todayerror.service;

import com.example.todayerror.domain.Post;
import com.example.todayerror.domain.User;
import com.example.todayerror.dto.PostDto.PostDto;
import com.example.todayerror.repository.PostRepository;
import com.example.todayerror.repository.UserRepository;
import com.example.todayerror.validator.PostValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {

    private final AwsS3Service awsS3Service;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<String> save(MultipartFile multipartFile, PostDto.SaveRequest postDto, User user) {
        PostValidator.validatePostSaveRegister(postDto, multipartFile, user);
        Map<String, String> map = awsS3Service.uploadFile(multipartFile);
        User joinUser = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("유효한 회원을 찾을 수 없습니다.")
        );
        Post post = postDto.toEntity();
        post = post.builder()
                .imageFilename(map.get("fileName"))
                .imageUrl(map.get("url"))
                .user(user)
                .nickName(user.getUsername())
                .build();
        postRepository.save(post);
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<PostDto> getAllPost() {
        List<Post> posts = postRepository.findAll();
        List<PostDto.MainResponse> postReponse = new ArrayList<>();
        for (Post post : posts) {
            PostDto.MainResponse postDto = PostDto.MainResponse.builder()
                    .postId(post.getId())
                    .nickName(post.getNickName())
                    .title(post.getTitle())
                    .category(post.getCategory())
                    .completed(post.getCompleted())
                    .createdAt(formmater(post.getCreatedAt()))
                    .imageUrl(post.getImageUrl())
                    .build();
            postReponse.add(postDto);
        }
        return new ResponseEntity(postReponse, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<PostDto> getCategoryPost(String category) {
        List<Post> getCategoryPost = postRepository.findByCategory(category);
        List<PostDto.MainResponse> categoryResponse = new ArrayList<>();
        for (Post post : getCategoryPost) {
            PostDto.MainResponse postDto = PostDto.MainResponse
                    .builder()
                    .nickName(post.getNickName())
                    .title(post.getTitle())
                    .category(post.getCategory())
                    .createdAt(formmater(post.getCreatedAt()))
                    .completed(post.getCompleted())
                    .imageUrl(post.getImageUrl())
                    .build();
            categoryResponse.add(postDto);
        }
        return new ResponseEntity(categoryResponse, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<PostDto> getPostDeatils(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("찾으시는 게시물이 존재하지 않습니다.")
        );
        PostDto.DetailResponse postDto = PostDto.DetailResponse
                .builder()
                .nickName(post.getNickName())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .createdAt(formmater(post.getCreatedAt()))
                .completed(post.getCompleted())
                .build();
        return new ResponseEntity(postDto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> update(Long postId, MultipartFile multipartFile, PostDto.PutRequest postDto) {
        PostValidator.validatePostPutRegister(multipartFile, postDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("찾으시는 게시글이 존재하지 않습니다."));
        awsS3Service.deleteFile(post.getImageFilename());
        Map<String, String> map = awsS3Service.uploadFile(multipartFile);
        post = postDto.toEntity();
        post.setImageUrl(map.get("url"));
        post.setImageFilename(map.get("fileName"));
        postRepository.save(post);
        return new ResponseEntity("success", HttpStatus.OK);
    }

    //Delete Complete
    @Transactional
    public ResponseEntity<String> delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("찾으시는 게시글이 존재하지 않습니다."));
        postRepository.delete(post);
        awsS3Service.deleteFile(post.getImageFilename());
        return new ResponseEntity("success", HttpStatus.OK);
    }

    public String formmater(LocalDateTime localDateTime){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDateTime);
    }
}