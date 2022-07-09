package com.simplecloudnote.controller;

import com.simplecloudnote.dao.LogDao;
import com.simplecloudnote.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("a/log")
public class LogController {

    @Autowired
    private LogDao logDao;

    @GetMapping("logs")
    public List<Log> toGetLogs(){
        List<Log> logList = logDao.findLogList();
        return logList;
    }
}
