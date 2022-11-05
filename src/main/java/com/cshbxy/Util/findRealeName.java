package com.cshbxy.Util;

import com.cshbxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class findRealeName {

    private static UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        findRealeName.userService = userService;
    }

    public static String findName(String uid) {
        return userService.findRealeName(uid);
    }
}
