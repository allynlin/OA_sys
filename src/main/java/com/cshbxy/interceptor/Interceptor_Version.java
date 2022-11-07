package com.cshbxy.interceptor;

import com.cshbxy.Util.I18nUtil;
import com.cshbxy.Util.Version;
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
            response.getWriter().write("{\"code\":101,\"message\":\"" + I18nUtil.getMessage("versionFail") + "\"}");
            return false;
        }
        // 判断 version 是否低于 lowVersion
        if (version.compareTo(Version.getLowVersion()) < 0) {
            response.getWriter().write("{\"code\":103,\"message\":\"" + I18nUtil.getMessage("versionLow") + "\"}");
            return true;
        }
        return true;
    }
}
