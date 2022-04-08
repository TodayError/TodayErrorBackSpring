package com.example.todayError.controller;


import com.example.todayError.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Controller
@RestController
public class PostController {

    private final PostService postService;

//    @PostMapping("/restaurant/{restaurantId}/foods")
//    public ResponseEntity<HttpStatus> posting(){
////        return postService.save();
//    }

}
