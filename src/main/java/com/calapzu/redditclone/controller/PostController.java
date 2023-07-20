package com.calapzu.redditclone.controller;

import com.calapzu.redditclone.dto.PostRequest;
import com.calapzu.redditclone.dto.PostResponse;
import com.calapzu.redditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest){
        postService.save(postRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("subredditId")
    public ResponseEntity<List<PostResponse>> getPostsBySubReddit(@RequestParam Long subredditId){
        return status(HttpStatus.OK).body(postService.getPostsBySubReddit(subredditId));
    }

    @GetMapping("username")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@RequestParam String username){
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }

}
