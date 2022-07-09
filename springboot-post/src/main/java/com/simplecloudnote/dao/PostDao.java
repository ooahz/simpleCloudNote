package com.simplecloudnote.dao;

import com.simplecloudnote.entity.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostDao {
    void insertPost(Post post);

    Post selectPostById(String id);

    List<Post> findPostList(String state, String mark);

    Post selectPostByTitle(String title);

    void updatePost(Post post);
}
