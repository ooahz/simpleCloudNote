package com.example.simplecloudnote;

import com.simplecloudnote.dao.PostDao;
import com.simplecloudnote.dao.UserDao;
import com.simplecloudnote.entity.Category;
import com.simplecloudnote.entity.Post;
import com.simplecloudnote.entity.User;
import com.simplecloudnote.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import com.simplecloudnote.Application;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Component
public class ApplicationTests {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PostDao postDao;

    @Test
    public void contextLoads() {
//        User user = userDao.getUserById(999);
//        System.out.println(user);
    }
    @Test
    public void selectTest(){
        User userByCode = userDao.getUserByID("999");
        System.out.println(userByCode);
    }
    @Test
    public void method(){
        Post post = postDao.selectPostById("1001");
        System.out.println(post);
    }
}
