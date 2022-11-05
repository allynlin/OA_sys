package com.cshbxy.controller;

import com.cshbxy.Util.JwtUtil;
import com.cshbxy.dao.*;
import com.cshbxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // 用户登录
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_all login(User apply) {
        try {
            User user = userService.login(apply);
            if (user != null) {
                switch (user.getStatus()) {
                    case 0:
                        user.setDepartmentUid(userService.findRealeName(user.getDepartmentUid()));
                        String token = JwtUtil.sign(user.getUid(), user.getUserType());
                        return new Message_all(200, "登录成功", user, token);
                    case -1:
                        return new Message_all(400, "用户已被禁用");
                    default:
                        return new Message_all(400, "状态异常");
                }
            }
            return new Message_all(400, "用户名或密码错误", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_all(500, "未知错误");
        }
    }

    // 校验用户，用于用户在 token 有效期内免重新登录
    @RequestMapping(value = "/checkUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_all checkUser(HttpServletRequest request) {
        try {
            String uid = JwtUtil.getUserUid(request.getHeader("Authorization"));
            User user = userService.findUserByUid(uid);
            if (user != null) {
                switch (user.getStatus()) {
                    case 0:
                        user.setDepartmentUid(userService.findRealeName(user.getDepartmentUid()));
                        String token = JwtUtil.sign(user.getUid(), user.getUserType());
                        return new Message_all(200, "自动登录成功", user, token);
                    case -1:
                        return new Message_all(400, "用户已被禁用");
                    default:
                        return new Message_all(400, "状态异常");
                }
            }
            return new Message_all(400, "用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_all(500, "未知错误");
        }
    }

    // 注册
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message add(User apply) {
        try {
            // 生成 uuid
            apply.setUid(UUID.randomUUID().toString());
            int result = userService.add(apply);
            if (result != 1) {
                return new Message(400, "注册失败");
            }
            return new Message(200, "注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 获取真实姓名
    @RequestMapping(value = "/findRealeName", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findRealeName(String uid) {
        try {
            String realName = userService.findRealeName(uid);
            if (realName != null) {
                return new Message_body(200, "获取成功", realName);
            }
            return new Message_body(400, "获取失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }

    // 校验当前用户名是否可用
    @RequestMapping(value = "/checkUsername", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message checkUsername(User apply) {
        try {
            String username = userService.checkUsername(apply);
            if (username != null) {
                return new Message(400, "用户名已存在");
            }
            return new Message(200, "用户名可用");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 更新用户信息
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message update(User apply) {
        try {
            int result = userService.update(apply);
            if (result != 1) {
                return new Message(400, "更新失败");
            }
            return new Message(200, "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 删除用户
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message delete(String uid) {
        try {
            int result = userService.delete(uid);
            if (result != 1) {
                return new Message(400, "删除失败");
            }
            return new Message(200, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 更新密码
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message updatePassword(User apply) {
        try {
            int result = userService.updatePassword(apply);
            if (result != 1) {
                return new Message(400, "更新失败");
            }
            return new Message(200, "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 更新用户名
    @RequestMapping(value = "/updateUsername", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message updateUsername(User apply) {
        try {
            String username = userService.checkUsername(apply);
            if (username != null) {
                return new Message(400, "用户名已存在");
            }
            int result = userService.updateUsername(apply);
            if (result != 1) {
                return new Message(400, "更新失败");
            }
            return new Message(200, "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 更新用户状态
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message updateStatus(User apply) {
        try {
            int result = userService.updateStatus(apply);
            if (result != 1) {
                return new Message(400, "更新失败");
            }
            return new Message(200, "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, "未知错误");
        }
    }

    // 获取用户上级列表
    @RequestMapping(value = "/findUserType", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findUserType() {
        try {
            List<User> list = userService.findUserType();
            if (list != null) {
                return new Message_body(200, "获取成功", list);
            }
            return new Message_body(400, "获取失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, "未知错误");
        }
    }
}
