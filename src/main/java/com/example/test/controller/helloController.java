package com.example.test.controller;

import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class helloController {
    @RequestMapping("/plot")
    public String login1(){
        return "plot";
    }

    @RequestMapping(value = "/loginIn",method = RequestMethod.POST)
    @ResponseBody
    public String login(String account, String password) throws IOException {
        if(account.equals("admin")&&password.equals("12345")){
            return "success";
        }
        else{
            return "error";
        }
    }
}
