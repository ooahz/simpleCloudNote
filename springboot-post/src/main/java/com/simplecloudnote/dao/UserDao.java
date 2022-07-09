package com.simplecloudnote.dao;

import com.simplecloudnote.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    User getUserByID(String id);

    User getUserByCode(String code);

    void updateUser(User user);

    void insertUser(User user);

    List<User> findUserList();
}
