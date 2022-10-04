package com.cshbxy.interceptor;

import com.cshbxy.Util.Version;
import com.cshbxy.dao.Message;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor_Version extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        // 获取 header 中的 version
        String version = request.getHeader("version");
        // 判断 version 是否为空
        if (version == null) {
            response.getWriter().write(new Message(101, "版本校验失败").toString());
            return false;
        }
        // 判断 version 是否低于 ver
        if (version.compareTo(Version.getVersion()) < 0) {
            response.getWriter().write(new Message(103, "版本过低").toString());
            return false;
        }
        return true;
    }
}
