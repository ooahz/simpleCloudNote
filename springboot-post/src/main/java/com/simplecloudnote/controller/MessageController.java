package com.simplecloudnote.controller;

import com.simplecloudnote.entity.User;
import com.simplecloudnote.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("a/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("send")
    public String toSend(HttpServletRequest request, @RequestBody String info){
        User user = (User) request.getSession().getAttribute("user");
        String result = messageService.save(user,info);
        return result;
    }

    @GetMapping("messages/{page}")
    public Map<String,Object> toGetMessages(@PathVariable String page){
        Map<String, Object> map = messageService.findList(Integer.parseInt(page));
        return map;
    }
}
