package com.example.demo.servicea.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SimpleSourceBean {
    @Autowired
    private Source source;

    public void sendMessage(String message){
        Map<String,String> map = new HashMap<>();
        map.put("message",message);
        source.output().send(MessageBuilder.withPayload(map).build());
    }
}
