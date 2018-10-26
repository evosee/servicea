package com.example.demo.servicea.controller;

import com.example.demo.servicea.message.SimpleSourceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private SimpleSourceBean simpleSourceBean;
    @RequestMapping("/send")
    public String sendMessage(String message){
        simpleSourceBean.sendMessage(message);
        return message;
    }
}
