package com.example.todayerror.controller;

import com.example.todayerror.domain.User;
import com.example.todayerror.dto.PostDto.PostDto;
import com.example.todayerror.security.UserDetailsImpl;
import com.example.todayerror.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @GetMapping("/main")
    public ResponseEntity<PostDto> getAllPost(){
        return postService.getAllPost();
    }

    @GetMapping("/main/category/{categoryId}")
    public ResponseEntity<PostDto> getCategoryPost(@PathVariable String categoryId){
        return new ResponseEntity(postService.getCategoryPost(categoryId) , HttpStatus.OK);
    }

    @GetMapping("details/{postId}")
    public ResponseEntity<PostDto> getPostDetails(@PathVariable Long postId){
        return new ResponseEntity(postService.getPostDeatils(postId) , HttpStatus.OK);
    }

    @PostMapping("/file")
    public ResponseEntity postSave(@RequestPart("file") MultipartFile multipartFile ,
                                   @RequestPart("information") PostDto.SaveRequest postDto ,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        return postService.save(multipartFile , postDto , user);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity postUpdate(@PathVariable Long postId ,
                                             @RequestPart("file") MultipartFile multipartFile,
                                             @RequestPart("information") PostDto.PutRequest postDto
                                             ,@AuthenticationPrincipal User user){
        return postService.update(postId , multipartFile , postDto , user);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity postDelete(@PathVariable Long postId , @AuthenticationPrincipal User user){
        return postService.delete(postId , user);
    }
}