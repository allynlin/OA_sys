package com.itheima.Util;

import java.util.Objects;

public class CheckToken {
    public static String isTeacher(String token) {
        try {
            // 解析 token，校验 token 是否正确，如果正确则返回 uid
            if (Objects.equals(JwtUtil.getUsertype(token), "teacher")) {
                // 将 uid 提交给 token 服务，重新生成新的 token
                return JwtUtil.sign(JwtUtil.getUserUid(token), "teacher");
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String isDepartment(String token) {
        try {
            // 解析 token，校验 token 是否正确，如果正确则返回 uid
            if (Objects.equals(JwtUtil.getUsertype(token), "department")) {
                // 将 uid 提交给 token 服务，重新生成新的 token
                return JwtUtil.sign(JwtUtil.getUserUid(token), "department");
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String isLeader(String token) {
        try {
            // 解析 token，校验 token 是否正确，如果正确则返回 uid
            if (Objects.equals(JwtUtil.getUsertype(token), "leader")) {
                // 将 uid 提交给 token 服务，重新生成新的 token
                return JwtUtil.sign(JwtUtil.getUserUid(token), "leader");
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
