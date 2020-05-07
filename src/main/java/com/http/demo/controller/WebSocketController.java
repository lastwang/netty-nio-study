package com.http.demo.controller;

import org.springframework.stereotype.Component;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketController {

    private Session session;

//    private Map<String, WebSocketSi>  webSocketSession = new ConcurrentHashMap<>();


}
