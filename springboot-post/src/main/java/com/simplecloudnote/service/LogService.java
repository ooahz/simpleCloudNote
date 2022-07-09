package com.simplecloudnote.service;

import com.simplecloudnote.dao.LogDao;
import com.simplecloudnote.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;

/**
* @author 十玖八柒
* @date 2022
* @GitHub https://github.com/ooahz
* @desc 日志功能
*/
@Service
public class LogService {

    @Autowired
    private LogDao logDao;

    /**
     * 保存日志
     * @param userCode
     * @param info
     */
    public void save(String userCode, String info){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String requestURI = ((ServletRequestAttributes) requestAttributes).getRequest().getRequestURI();
        Log log = new Log();
        log.setId(String.valueOf(System.currentTimeMillis()));
        log.setInfo(info);
        log.setUserCode(userCode);
        log.setDate(LocalDate.now());
        log.setUrl(requestURI);
        logDao.save(log);
    }
}
