package com.cshbxy.Util;

import com.cshbxy.dao.LoginRecord;
import com.cshbxy.service.LoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
public class LoginAnnal {
    private static LoginRecordService loginRecordService;

    @Autowired
    public void setLoginRecordService(LoginRecordService loginRecordService) {
        LoginAnnal.loginRecordService = loginRecordService;
    }

    public static void setLoginAnnal(HttpServletRequest request, String userUid, int type) {
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setUid(UUID.randomUUID().toString());
        loginRecord.setUserUid(userUid);
        loginRecord.setOsName(GetSysInfo.getOsName(request));
        loginRecord.setBrowser(GetSysInfo.getBrowserName(request));
        loginRecord.setIpAddress(GetSysInfo.getIpAddress(request));
        loginRecord.setType(type);
        loginRecordService.add(loginRecord);
    }
}