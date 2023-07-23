package com.ArkJJ.ChatApp.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

public class UserHandShakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(
            ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        try {
            System.out.println("Ahi viene el body: ");
            System.out.println(request.getBody().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request.getPrincipal();
    }

}
