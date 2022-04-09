package com.example.todayError.controller;
import com.example.todayError.domain.Post;
import com.example.todayError.dto.CommentDto;
import com.example.todayError.dto.PostDto.PostDto;
import com.example.todayError.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Post> getPostDetails(@PathVariable Long postId){
        return postService.getPostDeatils(postId);
    }

    @PostMapping("/file")
    public ResponseEntity<String> postUpload(@RequestPart("file") MultipartFile multipartFile ,
                                             @RequestPart("information") PostDto postDto){
        return postService.save(multipartFile , postDto);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<String> postUpdate(@PathVariable Long postId , @RequestBody PostDto postDto){
        return postService.update(postId , postDto);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> postDelete(@PathVariable Long postId){
        return postService.delete(postId);
    }
}
