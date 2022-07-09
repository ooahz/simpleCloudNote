package com.simplecloudnote.controller;

import com.simplecloudnote.entity.User;
import com.simplecloudnote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Map<String,Object> toLogin(@RequestBody User user, HttpServletRequest request) {
        Map<String, Object> result = userService.login(user, request);
        return result;
    }

    @PostMapping("register")
    public String toRegister(@RequestBody User user, HttpServletRequest request) {
        String result = userService.register(user, request);
        return result;
    }

    @GetMapping("logout")
    public void toLogout(HttpServletRequest request) {
        userService.logout(request);
    }

}
