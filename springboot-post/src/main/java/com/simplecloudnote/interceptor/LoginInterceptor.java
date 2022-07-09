package com.simplecloudnote.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.simplecloudnote.entity.User;
import com.simplecloudnote.utils.ReturnResult;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author 十玖八柒
* @date 2022
* @GitHub https://github.com/ooahz
* @desc 拦截器
*/
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if(ObjectUtil.isNotEmpty(user)){
            return true;
        }else{
//            用户未登录，将结果返回前端
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            String result = ReturnResult.result("logout", "未登录");
            response.getWriter().print(result);
            return false;
        }
    }
}
