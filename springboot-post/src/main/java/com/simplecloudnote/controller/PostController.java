package com.simplecloudnote.controller;

import com.simplecloudnote.entity.Post;
import com.simplecloudnote.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("a/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("save")
    public String toInsert(@RequestBody Post post) {
        String result = postService.save(post);
        return result;
    }

    @GetMapping("get/{id}")
    public Post toGet(@PathVariable String id) {
        Post post = postService.select(id);
        return post;
    }

    @GetMapping("posts/{state}/{page}")
    public HashMap<String, Object> toGetPosts(HttpServletRequest request, @PathVariable String state, @PathVariable Integer page) {
        HashMap<String, Object> list = postService.findList(state, page, request);
        return list;
    }

    @PostMapping("update")
    public String toUpdate(@RequestBody Post post) {
        String result = postService.update(post);
        return result;
    }

}
