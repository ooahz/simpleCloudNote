package com.simplecloudnote.dao;

import com.simplecloudnote.entity.PostContent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostContentDao {
    void insertPostContent(PostContent postContent);

    void updatePostContent(PostContent postContent);
}
