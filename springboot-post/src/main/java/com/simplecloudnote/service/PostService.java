package com.simplecloudnote.service;

import cn.hutool.core.util.ObjectUtil;
import com.simplecloudnote.dao.PostContentDao;
import com.simplecloudnote.dao.PostDao;
import com.simplecloudnote.entity.Post;
import com.simplecloudnote.entity.User;
import com.simplecloudnote.utils.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author 十玖八柒
 * @date 2022
 * @GitHub https://github.com/ooahz
 * @desc 文章功能
 */
@Service
public class PostService {
    @Autowired
    private PostDao postDao;
    @Autowired
    private PostContentDao postContentDao;

    /**
     * 保存文章
     *
     * @param post
     * @return
     */
    @Transactional
    public String save(Post post) {

        Post post1 = postDao.selectPostByTitle(post.getTitle());
        if (ObjectUtil.isNotEmpty(post1)) {
            return ReturnResult.result("false", "文章标题已存在！");
        }
        if (ObjectUtil.isEmpty(post.getCreateDate())) {
            post.setCreateDate(LocalDate.now());
        }
        String id = String.valueOf(System.currentTimeMillis());
        post.setId(id);
//        post.setCreateDate(LocalDate.now());
        post.setUpdateDate(LocalDate.now());
        post.getPostContent().setPostId(id);


        postDao.insertPost(post);
        postContentDao.insertPostContent(post.getPostContent());

        return ReturnResult.result("true", "发布成功！");
    }

    /**
     * 获取对应id文章
     *
     * @param id
     * @return
     */
    public Post select(String id) {
        Post post = postDao.selectPostById(id);
        return post;
    }

    /**
     * 获取对应状态的文章 0：待删除；1：待发布；2；已发布
     *
     * @param state
     * @return
     */
    public HashMap<String, Object> findList(String state, Integer page, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String userMark = user.getUserMark();

        HashMap<String, Object> map = new HashMap<>();
        if (ObjectUtil.isEmpty(state)) {
            state = "2";
        }
        List<Post> postList = postDao.findPostList(state, userMark);

//        分页
        Stream<Post> stream = postList.stream().skip((page - 1) * 10L).limit(10);
//        总数
        int size = postList.size();

        map.put("post", stream);
        map.put("total", size);

        return map;
    }

    /**
     * 更新文章
     *
     * @param post
     * @return
     */
    @Transactional
    public String update(Post post) {
        post.setUpdateDate(LocalDate.now());
        postDao.updatePost(post);
        if (post.getState().equals("0")) {
            return ReturnResult.result("true", "删除成功！");
        }
//        删除操作不需要修改文章内容详情
        postContentDao.updatePostContent(post.getPostContent());
        return ReturnResult.result("true", "更新成功！");
    }

}
