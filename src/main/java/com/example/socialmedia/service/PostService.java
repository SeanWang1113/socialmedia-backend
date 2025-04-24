package com.example.socialmedia.service;


import com.example.socialmedia.Post;
import com.example.socialmedia.repository.PostRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public void createPost(Long userId, String content){
        Post post = new Post();
        post.setUserId(userId);
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public boolean updatePost(Long userId, Long postId, String newContent) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            if (post.getUserId().equals(userId)) {
                post.setContent(newContent);
                postRepository.save(post);
                return true;
            }
        }
        return false;
    }

    public boolean deletePost(Long userId, Long postId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            if (post.getUserId().equals(userId)) {
                postRepository.delete(post);
                return true;
            }
        }
        return false;
    }


}
