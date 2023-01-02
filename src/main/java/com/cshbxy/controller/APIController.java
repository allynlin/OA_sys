package com.cshbxy.controller;

import com.cshbxy.Util.I18nUtil;
import com.cshbxy.dao.Message_body;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class APIController {

    @RequestMapping(value = "/getUUID", method = RequestMethod.GET)
    @ResponseBody
    public Message_body getUUID(HttpServletRequest request) {
        String lang = request.getHeader("language");
        return new Message_body(200, I18nUtil.getMessage("getUUIDSuccess"), UUID.randomUUID().toString());
    }
}
