package com.simplecloudnote.controller;


import com.simplecloudnote.entity.User;
import com.simplecloudnote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("a/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("users")
    public List<User> getUsers() {
        List<User> userList = userService.findList();
        return userList;
    }

    @PostMapping("update")
    public String toDelete(User user, HttpServletRequest request) {
        String operation = request.getParameter("operation");
        if ("stateChange".equals(operation)) {
            return userService.changeState(user, request);
        }
        return userService.update(user, request);
    }

    @GetMapping("get")
    public User toGet(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user;
    }

    @PostMapping("upload")
    public String toUpload(@RequestBody MultipartFile file, HttpServletRequest request) {
        String result = userService.upload(file, request);
        return result;
    }

}
