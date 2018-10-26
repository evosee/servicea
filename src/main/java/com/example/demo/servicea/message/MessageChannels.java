package com.example.demo.servicea.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessageChannels {
    String OUTPUT = "myOutput";
    @Output(MessageChannels.OUTPUT)
    MessageChannel orgs();
}
