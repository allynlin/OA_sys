package com.cshbxy.Util;

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

    // 校验是否是对应用户
    public static boolean checkIsTeacher(String token) {
        try {
            // 解析 token，校验 token 是否为 teacher
            return !Objects.equals(JwtUtil.getUsertype(token), "teacher");
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean checkIsDepartment(String token) {
        try {
            // 解析 token，校验 token 是否为 department
            return Objects.equals(JwtUtil.getUsertype(token), "department");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkIsLeader(String token) {
        try {
            // 解析 token，校验 token 是否为 leader
            return Objects.equals(JwtUtil.getUsertype(token), "leader");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
