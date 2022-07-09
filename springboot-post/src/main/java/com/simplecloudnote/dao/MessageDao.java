package com.simplecloudnote.dao;

import com.simplecloudnote.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageDao {
    List<Message> findMessageList();

    void insertMessage(Message message);
}
