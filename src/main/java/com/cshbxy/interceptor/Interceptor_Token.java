package com.cshbxy.interceptor;

import com.cshbxy.Util.JwtUtil;
import com.cshbxy.dao.Message;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor_Token extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        // 获取 header 中的 token
        String token = request.getHeader("Authorization");
        // 判断 token 是否为空
        if (token == null) {
            response.getWriter().write("{\"code\":401,\"message\":\"未登录\"}");
            return false;
        }
        // 判断 token 是否正确
        if (!JwtUtil.isTokenTrue(token)) {
            response.getWriter().write("{\"code\":403,\"message\":\"token校验失败\"}");
            return false;
        }
        return true;
    }
}
