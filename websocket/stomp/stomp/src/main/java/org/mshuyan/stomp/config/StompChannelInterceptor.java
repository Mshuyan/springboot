package org.mshuyan.stomp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.security.Principal;
import java.util.Objects;

/**
 * STOMP连接拦截处理
 *
 * @author shuyan
 */
@Slf4j
public class StompChannelInterceptor implements ChannelInterceptor {
    @Override
    public void postSend(@NonNull Message<?> message, @NonNull MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        assert accessor != null;
        String sessionId = accessor.getSessionId();
        switch (Objects.requireNonNull(accessor.getCommand())) {
            case CONNECT -> connect(sessionId, message);
            case CONNECTED -> log.info("Current Connect CONNECTED Status:sessionId->{}", sessionId);
            case DISCONNECT -> disconnect(sessionId, accessor.getDestination());
            case SUBSCRIBE -> subscribe(accessor);
            default -> {
            }
        }
    }

    /**
     * 连接成功
     *
     * @param sessionId 会话id
     */
    @SuppressWarnings("unused")
    private void connect(String sessionId, Message<?> message) {
        log.info("############ Connect stomp websocket, [sessionId:{}] ############", sessionId);
    }

    /**
     * 断开连接
     *
     * @param sessionId 会话id
     */
    @SuppressWarnings("unused")
    private void disconnect(String sessionId, String destination) {
        log.info("############ Disconnect stomp websocket, [sessionId:{}] ############", sessionId);
    }

    private void subscribe(StompHeaderAccessor accessor) {
        String topic = accessor.getDestination();
        log.info("############ Connect stomp subscribe websocket, [Topic:{}], [sessionId:{}] ############", topic, accessor.getSessionId());
    }

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel messageChannel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        /*
         * 1. 判断是否为首次连接请求，如果已经连接过，直接返回message
         * 2. 网上有种写法是在这里封装认证用户的信息，本文是在http阶段，websockt 之前就做了认证的封装，所以这里直接取的信息
         */
        assert accessor != null;
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // 这里可以做登录验证逻辑，除了getLogin()可以getPasscode()拿到密码做登录验证。
            Principal user = accessor::getLogin;
            // 设置用户
            accessor.setUser(user);
            log.info("用户:" + user + " 建立连接");
        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            log.info("用户:" + accessor.getUser() + " 断开连接");
        }
        return message;
    }
}