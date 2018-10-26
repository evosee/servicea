package com.example.demo.servicea.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableBinding(MessageChannels.class)
public class MyMessageSourceBean {
    @Autowired
    private MessageChannels messageChannels;

    public void sendMessage(String message){
        Map<String,String> map = new HashMap<>();
        map.put("message",message);
        messageChannels.orgs().send(MessageBuilder.withPayload(map).build());
    }
}
