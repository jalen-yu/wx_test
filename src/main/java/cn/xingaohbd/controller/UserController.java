package cn.xingaohbd.controller;

import cn.xingaohbd.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/login")
    public String userLogin(String code, HttpSession session){
        String str = userService.login(code);
        log.info("---"+str);
        JsonParser parser = new JsonParser();
        JsonObject json =(JsonObject) parser.parse(str);
        if(StringUtils.isEmpty(json.get("errcode"))){
            session.setAttribute("id",json.get("openid").toString());
            return json.get("openid").toString();
        }else{
            return null;
        }
    }

}

