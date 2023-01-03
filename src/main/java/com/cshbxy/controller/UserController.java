package com.cshbxy.controller;

import com.cshbxy.Util.I18nUtil;
import com.cshbxy.Util.JwtUtil;
import com.cshbxy.dao.Message;
import com.cshbxy.dao.Message_all;
import com.cshbxy.dao.Message_body;
import com.cshbxy.dao.User;
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
                        return new Message_all(200, I18nUtil.getMessage("loginSuccess"), user, token);
                    case -1:
                        return new Message_all(400, I18nUtil.getMessage("userDisabled"));
                    default:
                        return new Message_all(400, I18nUtil.getMessage("stateError"));
                }
            }
            return new Message_all(400, I18nUtil.getMessage("loginFail"), null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_all(500, I18nUtil.getMessage("systemError"));
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
                        return new Message_all(200, I18nUtil.getMessage("autoLoginSuccess"), user, token);
                    case -1:
                        return new Message_all(400, I18nUtil.getMessage("userDisabled"));
                    default:
                        return new Message_all(400, I18nUtil.getMessage("stateError"));
                }
            }
            return new Message_all(400, I18nUtil.getMessage("noUser"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_all(500, I18nUtil.getMessage("systemError"));
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
                return new Message(400, I18nUtil.getMessage("registerFail"));
            }
            return new Message(200, I18nUtil.getMessage("registerSuccess"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, I18nUtil.getMessage("systemError"));
        }
    }

    // 获取真实姓名
    @RequestMapping(value = "/findRealeName", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findRealeName(String uid) {
        try {
            String realName = userService.findRealeName(uid);
            if (realName != null) {
                return new Message_body(200, I18nUtil.getMessage("getSuccess"), realName);
            }
            return new Message_body(400, I18nUtil.getMessage("getFail"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, I18nUtil.getMessage("systemError"));
        }
    }

    // 校验当前用户名是否可用
    @RequestMapping(value = "/checkUsername", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message checkUsername(User apply) {
        try {
            String username = userService.checkUsername(apply);
            if (username != null) {
                return new Message(400, I18nUtil.getMessage("usernameExists"));
            }
            return new Message(200, I18nUtil.getMessage("usernameCanUse"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, I18nUtil.getMessage("systemError"));
        }
    }

    // 更新用户信息
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message update(User apply) {
        try {
            int result = userService.update(apply);
            if (result != 1) {
                return new Message(400, I18nUtil.getMessage("updateFail"));
            }
            return new Message(200, I18nUtil.getMessage("updateSuccess"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, I18nUtil.getMessage("systemError"));
        }
    }

    // 删除用户
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message delete(String uid) {
        try {
            int result = userService.delete(uid);
            if (result != 1) {
                return new Message(400, I18nUtil.getMessage("deleteFail"));
            }
            return new Message(200, I18nUtil.getMessage("deleteSuccess"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, I18nUtil.getMessage("systemError"));
        }
    }

    // 更新密码
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message updatePassword(User apply) {
        try {
            int result = userService.updatePassword(apply);
            if (result != 1) {
                return new Message(400, I18nUtil.getMessage("updateFail"));
            }
            return new Message(200, I18nUtil.getMessage("updateSuccess"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, I18nUtil.getMessage("systemError"));
        }
    }

    // 更新用户名
    @RequestMapping(value = "/updateUsername", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message updateUsername(User apply) {
        try {
            String username = userService.checkUsername(apply);
            if (username != null) {
                return new Message(400, I18nUtil.getMessage("usernameExists"));
            }
            int result = userService.updateUsername(apply);
            if (result != 1) {
                return new Message(400, I18nUtil.getMessage("updateFail"));
            }
            return new Message(200, I18nUtil.getMessage("updateSuccess"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, I18nUtil.getMessage("systemError"));
        }
    }

    // 更新用户状态
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message updateStatus(User apply) {
        try {
            int result = userService.updateStatus(apply);
            if (result != 1) {
                return new Message(400, I18nUtil.getMessage("updateFail"));
            }
            return new Message(200, I18nUtil.getMessage("updateSuccess"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, I18nUtil.getMessage("systemError"));
        }
    }

    // 获取用户上级列表
    @RequestMapping(value = "/findUserType", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findUserType() {
        try {
            List<User> list = userService.findUserType();
            if (list.size() != 0) {
                return new Message_body(200, I18nUtil.getMessage("getSuccess"), list);
            }
            return new Message_body(400, I18nUtil.getMessage("noUserList"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, I18nUtil.getMessage("systemError"));
        }
    }

    // 获取所有用户
    @RequestMapping(value = "/findAllUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findAllUser() {
        try {
            List<User> list = userService.findAllUser();
            if (list.size() != 0) {
                for (User user : list) {
                    user.setDepartmentUid(userService.findRealeName(user.getDepartmentUid()));
                }
                return new Message_body(200, I18nUtil.getMessage("getSuccess"), list);
            }
            return new Message_body(300, I18nUtil.getMessage("noUserList"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, I18nUtil.getMessage("systemError"));
        }
    }

    // 获取部门下的所有用户
    @RequestMapping(value = "/findAllUserByDepartmentUid", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findAllUserByDepartmentUid(String departmentUid) {
        try {
            List<User> list = userService.findAllUserByDepartmentUid(departmentUid);
            if (list.size() != 0) {
                return new Message_body(200, I18nUtil.getMessage("getSuccess"), list);
            }
            return new Message_body(300, I18nUtil.getMessage("noUserList"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, I18nUtil.getMessage("systemError"));
        }
    }

    // 获取部门下的所有领导
    @RequestMapping(value = "/findAllLeaderByDepartmentUid", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findAllLeaderByDepartmentUid(String departmentUid) {
        try {
            List<User> list = userService.findAllLeaderByDepartmentUid(departmentUid);
            if (list.size() != 0) {
                return new Message_body(200, I18nUtil.getMessage("getSuccess"), list);
            }
            return new Message_body(300, I18nUtil.getMessage("noUserList"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, I18nUtil.getMessage("systemError"));
        }
    }

    // 修改部门直属领导
    @RequestMapping(value = "/updateDepartmentLeader", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message updateDepartmentLeader(User apply) {
        try {
            userService.deleteDepartmentKey(apply.getDepartmentUid());
            int result = userService.updateDepartmentLeader(apply);
            if (result != 1) {
                return new Message(400, I18nUtil.getMessage("updateFail"));
            }
            return new Message(200, I18nUtil.getMessage("updateSuccess"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(500, I18nUtil.getMessage("systemError"));
        }
    }

    // 获取所有审批人
    @RequestMapping(value = "/findProcessUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Message_body findProcessUser() {
        try {
            List<User> list = userService.findProcessUser();
            if (list != null) {
                return new Message_body(200, I18nUtil.getMessage("getSuccess"), list);
            }
            return new Message_body(400, I18nUtil.getMessage("getFail"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Message_body(500, I18nUtil.getMessage("systemError"));
        }
    }
}
