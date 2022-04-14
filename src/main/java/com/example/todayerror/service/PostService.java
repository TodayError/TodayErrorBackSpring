package com.example.todayerror.service;

import com.example.todayerror.domain.Post;
import com.example.todayerror.domain.User;
import com.example.todayerror.dto.PostDto.PostDto;
import com.example.todayerror.repository.PostRepository;
import com.example.todayerror.repository.UserRepository;
import com.example.todayerror.security.jwt.JwtDecoder;
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
    private final JwtDecoder jwtDecoder;

    @Transactional
    public ResponseEntity<HttpStatus> save(MultipartFile multipartFile, PostDto.SaveRequest postDto, String user) {
        String username = jwtDecoder.decodeUsername(user);
        PostValidator.validatePostSaveRegister(postDto, multipartFile, username);
        Map<String, String> map = awsS3Service.uploadFile(multipartFile);
        User joinUser = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("유효한 회원을 찾을 수 없습니다."));
        Post post = Post.builder()
                .imageFilename(map.get("fileName"))
                .imageUrl(map.get("url"))
                .nickName(joinUser.getUsername())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .category(postDto.getCategory())
                .completed(postDto.getCompleted())
                .user(joinUser)
                .build();
        postRepository.save(post);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<PostDto> getAllPost() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostDto.MainResponse> postReponse = new ArrayList<>();
        for (Post post : posts) {
            PostDto.MainResponse postDto = PostDto.MainResponse.builder()
                    .postId(post.getId())
                    .nickName(post.getNickName())
                    .title(post.getTitle())
                    .category(post.getCategory())
                    .completed(post.getCompleted())
                    .modifiedAt(formmater(post.getModifiedAt()))
                    .imageUrl(post.getImageUrl())
                    .build();
            postReponse.add(postDto);
        }
        return new ResponseEntity(postReponse, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<PostDto> getCategoryPost(String category) {
        List<Post> getCategoryPost = postRepository.findByCategoryOrderByCreatedAtDesc(category);
        List<PostDto.MainResponse> categoryResponse = new ArrayList<>();
        for (Post post : getCategoryPost) {
            PostDto.MainResponse postDto = PostDto.MainResponse
                    .builder()
                    .nickName(post.getNickName())
                    .title(post.getTitle())
                    .category(post.getCategory())
                    .modifiedAt(formmater(post.getModifiedAt()))
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
                .modifiedAt(formmater(post.getModifiedAt()))
                .completed(post.getCompleted())
                .build();
        return new ResponseEntity(postDto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<HttpStatus> update(Long postId, MultipartFile multipartFile, PostDto.PutRequest postDto , String user) {
        String username = jwtDecoder.decodeUsername(user);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("찾으시는 게시글이 존재하지 않습니다."));
        awsS3Service.deleteFile(post.getImageFilename());
        validateCheckUser(username, post);
        PostValidator.validatePostPutRegister(multipartFile, postDto);
        Map<String, String> map = awsS3Service.uploadFile(multipartFile);

        post.update(postDto , map.get("url") , map.get("fileName"));
        return new ResponseEntity(HttpStatus.OK);
    }

    //Delete Complete
    @Transactional
    public ResponseEntity<HttpStatus> delete(Long postId, String user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("찾으시는 게시글이 존재하지 않습니다."));
        String username = jwtDecoder.decodeUsername(user);
        validateCheckUser(username, post);
        postRepository.delete(post);
        awsS3Service.deleteFile(post.getImageFilename());
        return new ResponseEntity(HttpStatus.OK);
    }

    public String formmater(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDateTime);
    }

    private void validateCheckUser(String user, Post post) {
        if (!user.equals(post.getNickName())) {
            throw new IllegalArgumentException("삭제 권한이 없는 유저 입니다.");
        }
    }
}