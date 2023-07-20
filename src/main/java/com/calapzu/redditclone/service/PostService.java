package com.calapzu.redditclone.service;

import com.calapzu.redditclone.dto.PostRequest;
import com.calapzu.redditclone.dto.PostResponse;
import com.calapzu.redditclone.exceptions.PostNotFoundException;
import com.calapzu.redditclone.exceptions.SubRedditNotFoundException;
import com.calapzu.redditclone.mapper.PostMapper;
import com.calapzu.redditclone.model.Post;
import com.calapzu.redditclone.model.SubReddit;
import com.calapzu.redditclone.model.User;
import com.calapzu.redditclone.repository.PostRepository;
import com.calapzu.redditclone.repository.SubredditRepository;
import com.calapzu.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    public void save(PostRequest postRequest) {
        SubReddit subReddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubRedditNotFoundException(postRequest.getSubredditName()));
        postRepository.save(postMapper.map(postRequest, subReddit, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubReddit(Long subRedditId){
        SubReddit subReddit = subredditRepository.findById(subRedditId)
                .orElseThrow(() -> new SubRedditNotFoundException(subRedditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subReddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
