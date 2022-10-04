package com.cshbxy.controller;

import com.cshbxy.Util.Version;
import com.cshbxy.dao.Message_body;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Controller
public class VersionController {
    @RequestMapping(value = "/getVersion", method = RequestMethod.GET)
    @ResponseBody
    public Message_body getVersion() {
        return new Message_body(200, "获取服务器版本成功", Version.getVersion());
    }
}
