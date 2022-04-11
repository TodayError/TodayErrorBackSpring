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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PostService {

    private final AwsS3Service awsS3Service;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<String> save(MultipartFile multipartFile, PostDto.SaveRequest postDto, User user) {
        PostValidator.validatePostSaveRegister(postDto , multipartFile , user);
        Map<String, String> map = awsS3Service.uploadFile(multipartFile);
        User joinUser = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("유효한 회원을 찾을 수 없습니다.")
        );
        postDto.setUser(joinUser);
        postDto.setNickName(joinUser.getUsername());
        postDto.setImageUrl(map.get("url"));
        postDto.setImageName(map.get("fileName"));
        Post post = postDto.toEntity();
        postRepository.save(post);

        return new ResponseEntity("게시글 저장이 정상적으로 완료되었습니다.", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<PostDto> getAllPost(){
        List<Post> posts = postRepository.findAll();
        List<PostDto.MainResponse> postReponse = new ArrayList<>();
        for (Post post : posts) {
            PostDto.MainResponse postDto = PostDto.MainResponse.builder()
                    .nickName(post.getNickName())
                    .title(post.getTitle())
                    .category(post.getCategory())
                    .completed(post.getCompleted())
                    .createdAt(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(post.getCreatedAt()))
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
            PostDto.MainResponse postDto = PostDto.MainResponse.builder()
                    .nickName(post.getNickName())
                    .title(post.getTitle())
                    .category(post.getCategory())
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
                .createdAt(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(post.getCreatedAt()))
                .completed(post.getCompleted())
                .comment(post.getComment())
                .build();

        return new ResponseEntity(postDto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> update(Long postId, MultipartFile multipartFile, PostDto.PutRequest postDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("찾으시는 게시글이 존재하지 않습니다.")
        );
        awsS3Service.deleteFile(post.getImageFilename());
        Map<String, String> map = awsS3Service.uploadFile(multipartFile);
        post = post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .imageUrl(map.get("url"))
                .category(postDto.getCategory())
                .completed(postDto.getCompleted())
                .imageFilename(map.get("fileName"))
                .build();
        postRepository.save(post);
        return new ResponseEntity("게시글 수정이 정상적으로 완료되었습니다.", HttpStatus.OK);
    }

    //Delete Complete
    @Transactional
    public ResponseEntity<String> delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("찾으시는 게시글이 존재하지 않습니다."));
        postRepository.delete(post);
        awsS3Service.deleteFile(post.getImageFilename());
        return new ResponseEntity("게시글 삭제가 정상적으로 완료되었습니다.", HttpStatus.OK);
    }
}
