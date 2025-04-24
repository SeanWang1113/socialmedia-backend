package com.example.socialmedia.controller;


import com.example.socialmedia.Post;
import com.example.socialmedia.auth.SessionManager;
import com.example.socialmedia.service.CommentService;
import com.example.socialmedia.service.PostService;
import com.example.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestParam String userName,
            @RequestParam String email,
            @RequestParam String phoneNumber){

        boolean result = userService.registerUser(userName, email, phoneNumber);

        if(result){
            return ResponseEntity.ok("註冊成功");
        }
        else{
            return ResponseEntity.badRequest().body("信箱已被註冊");
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String phoneNumber){
        String token = userService.login(phoneNumber);

        if(token != null){
            return ResponseEntity.ok("登入成功" + token);
        }
        else{
            return ResponseEntity.status(401).body("登入失敗");
        }

    }

    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestHeader("token") String token) {
        if (!SessionManager.isValidToken(token)) {
            return ResponseEntity.status(401).body("未授權，請先登入");
        }

        Long userId = SessionManager.getUserId(token);
        // 實作發文邏輯
        return ResponseEntity.ok("使用者 " + userId + " 發文成功");
    }

    @PostMapping("/post/create")
    public ResponseEntity<String> createPost(
            @RequestHeader("token") String token,
            @RequestParam String content) {

        if (!SessionManager.isValidToken(token)) {
            return ResponseEntity.status(401).body("未授權，請先登入");
        }

        Long userId = SessionManager.getUserId(token);
        postService.createPost(userId, content);
        return ResponseEntity.ok("發文成功");
    }

    @GetMapping("/post/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }


    @PutMapping("/post/edit")
    public ResponseEntity<String> editPost(
            @RequestHeader("token") String token,
            @RequestParam Long postId,
            @RequestParam String newContent) {

        if (!SessionManager.isValidToken(token)) {
            return ResponseEntity.status(401).body("未授權，請先登入");
        }

        Long userId = SessionManager.getUserId(token);
        boolean success = postService.updatePost(userId, postId, newContent);

        if (success) {
            return ResponseEntity.ok("貼文已更新");
        } else {
            return ResponseEntity.status(403).body("只能編輯自己的貼文");
        }
    }


    @DeleteMapping("/post/delete")
    public ResponseEntity<String> deletePost(
            @RequestHeader("token") String token,
            @RequestParam Long postId) {

        if (!SessionManager.isValidToken(token)) {
            return ResponseEntity.status(401).body("未授權，請先登入");
        }

        Long userId = SessionManager.getUserId(token);
        boolean success = postService.deletePost(userId, postId);

        if (success) {
            return ResponseEntity.ok("貼文已刪除");
        } else {
            return ResponseEntity.status(403).body("只能刪除自己的貼文");
        }
    }

    @PostMapping("/comment/add")
    public ResponseEntity<String> addComment(
            @RequestHeader("token") String token,
            @RequestParam Long postId,
            @RequestParam String content) {

        if (!SessionManager.isValidToken(token)) {
            return ResponseEntity.status(401).body("未授權，請先登入");
        }

        Long userId = SessionManager.getUserId(token);
        commentService.addComment(userId, postId, content);
        return ResponseEntity.ok("留言成功");
    }

}
