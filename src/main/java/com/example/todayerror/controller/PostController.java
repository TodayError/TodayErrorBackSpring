package com.example.todayerror.controller;

import com.example.todayerror.dto.PostDto.PostDto;
import com.example.todayerror.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
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
    public ResponseEntity<HttpStatus> postSave(@RequestPart("file") MultipartFile multipartFile ,
                                   @RequestPart("information") PostDto.SaveRequest postDto ,
                                   @RequestHeader("Authorization") String user){
        return postService.save(multipartFile , postDto , user);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<HttpStatus> postUpdate(@PathVariable Long postId ,
                                             @RequestPart("file") MultipartFile multipartFile,
                                             @RequestPart("information") PostDto.PutRequest postDto,
                                                 @RequestHeader("Authorization") String user){
        return postService.update(postId , multipartFile , postDto , user);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<HttpStatus> postDelete(@PathVariable Long postId , @RequestHeader("Authorization") String user){
        return postService.delete(postId , user);
    }
}