package com.cshbxy.interceptor;

import com.cshbxy.Util.CheckToken;
import com.cshbxy.dao.Message;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor_Token_Teacher extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        // 判断是否 teacher
        if (CheckToken.checkIsTeacher(request.getHeader("Authorization"))) {
            response.getWriter().write(new Message(403, "token校验失败").toString());
            return false;
        }
        return true;
    }
}
