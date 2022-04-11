package com.example.todayerror.controller;

import com.example.todayerror.domain.User;
import com.example.todayerror.dto.PostDto.PostDto;
import com.example.todayerror.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @GetMapping("/main")
    public ResponseEntity getAllPost(){
        return postService.getAllPost();
    }

    @GetMapping("/main/category/{categoryId}")
    public ResponseEntity getCategoryPost(@PathVariable String categoryId){
        return postService.getCategoryPost(categoryId);
    }

    @GetMapping("details/{postId}")
    public ResponseEntity<PostDto> getPostDetails(@PathVariable Long postId){
        return postService.getPostDeatils(postId);
    }

    @PostMapping("/file")
    public ResponseEntity<String> postSave(@RequestPart("file") MultipartFile multipartFile ,
                                             @RequestPart("information") PostDto.SaveRequest postDto ,
                                             @AuthenticationPrincipal User user){
        return postService.save(multipartFile , postDto , user);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<String> postUpdate(@PathVariable("postId") Long postId ,
                                             @RequestPart("file") MultipartFile multipartFile,
                                             @RequestPart("information") PostDto.PutRequest postDto){
        return postService.update(postId , multipartFile , postDto);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> postDelete(@PathVariable Long postId){
        return postService.delete(postId);
    }
}