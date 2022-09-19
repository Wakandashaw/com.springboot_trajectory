package com.example.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class helloController {
    @RequestMapping("/index")
    public String sayHello(){
        return "index";
    }
}
