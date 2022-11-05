package com.cshbxy.Util;

import java.util.Objects;

public class CheckToken {

    // 校验是否是对应用户
    public static boolean checkIsEmployee(String token) {
        try {
            // 解析 token，校验 token 是否为 Employee
            return !Objects.equals(JwtUtil.getUsertype(token), "Employee");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkIsDepartment(String token) {
        try {
            // 解析 token，校验 token 是否为 department
            return !Objects.equals(JwtUtil.getUsertype(token), "Department");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkIsLeader(String token) {
        try {
            // 解析 token，校验 token 是否为 leader
            return !Objects.equals(JwtUtil.getUsertype(token), "Leader");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
