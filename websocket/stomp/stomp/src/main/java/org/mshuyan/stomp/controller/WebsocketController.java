package org.mshuyan.stomp.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
public class WebsocketController {
    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/msg")
    public void messageMapping(Principal principal) {
        log.info("principal:{}",principal);
    }

    @SubscribeMapping("/msg")
    public String subscribeMapping() {
        log.info("subscribeMappingSendTo");
        return "received";
    }

    @MessageMapping("/msg-to")
    @SendTo("/topic/to")
    public String messageMappingSendTo(String message) {
        log.info("messageMappingSendTo:{}",message);
        return "receive: " + message;
    }

    @MessageMapping("/msg-to-user")
    @SendToUser("/topic/chat")
    public String messageMappingSendToUser(String message) {
        log.info("messageMappingSendToUser:{}",message);
        return "receive: " + message;
    }

    @MessageMapping("/msg-send")
    public String messageMappingSend(Message<String> message) {
        log.info("messageMappingSend:{}",message);
        simpMessagingTemplate.convertAndSend("/topic/to",message);
        return "receive: " + message;
    }

    @MessageMapping("/msg-send-void")
    public void messageMappingSendVoid(Message<String> message) {
        log.info("messageMappingSendVoid:{}",message);
        simpMessagingTemplate.convertAndSend("/topic/to",message);
    }

    @MessageMapping("/msg-send-user-void")
    public void messageMappingSendUserVoid(Message<String> message) {
        log.info("messageMappingSendUserVoid:{}",message);
        simpMessagingTemplate.convertAndSendToUser("123","/topic/chat",message);
    }

    @MessageMapping("/msg-exception")
    public void messageMappingException(Message<String> message) {
        throw new RuntimeException("test");
    }

    @MessageExceptionHandler(Exception.class)
    @SendTo("/topic/exception")
    public Exception handleExceptions(Exception t){
        t.printStackTrace();
        return t;
    }
}

