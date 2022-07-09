package com.simplecloudnote.dao;

import com.simplecloudnote.entity.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogDao {
    List<Log> findLogList();

    void save(Log log);
}
