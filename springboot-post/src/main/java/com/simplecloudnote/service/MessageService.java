package com.simplecloudnote.service;

import cn.hutool.core.util.ObjectUtil;
import com.simplecloudnote.dao.MessageDao;
import com.simplecloudnote.entity.Message;
import com.simplecloudnote.entity.User;
import com.simplecloudnote.utils.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
* @author 十玖八柒
* @date 2022
* @GitHub https://github.com/ooahz
* @desc 留言功能
*/
@Service
public class MessageService {
    @Autowired
    private MessageDao messageDao;

    /**
     * 将当前信息保存至当前登录用户下
     * @param user
     * @param info
     * @return
     */
    public String save(User user, String info){
        String userCode = user.getUserCode();
        Message message = new Message();
        message.setId(String.valueOf(System.currentTimeMillis()));
        message.setUserCode(userCode);
        message.setMessage(info);
        message.setCreateDate(LocalDate.now());
        messageDao.insertMessage(message);
        return ReturnResult.result("true","发送成功");
    }

    public Map<String,Object> findList(@RequestBody Integer page){
        if(ObjectUtil.isEmpty(page)){
            page=1;
        }
        HashMap<String, Object> map = new HashMap<>();
        List<Message> messageList = messageDao.findMessageList();

//        分页
        Stream<Message> messageStream = messageList.stream().skip((page - 1) * 10L).limit(10);
//        总数
        int size = messageList.size();

        map.put("messages",messageStream);
        map.put("total",size);
        return map;
    };

}
