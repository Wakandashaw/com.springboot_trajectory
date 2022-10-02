package com.example.test.controller;

import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class helloController {
    @RequestMapping(value = "/loginIn",method = RequestMethod.POST)
//    @ResponseBody
    public String login(String account, String password){
        if(account.equals("admin")&&password.equals("12345")){
            System.out.println("success");
            return "plot";
        }
        else{
            return "error";
        }
    }
}
