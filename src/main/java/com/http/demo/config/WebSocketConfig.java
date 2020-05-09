package com.http.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.http.HttpSessionListener;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig extends ServerEndpointConfig.Configurator implements HandshakeInterceptor {

    private static Logger log = LoggerFactory.getLogger(WebSocketConfig.class);

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        sec.getUserProperties().put("user", null);
        //sec.getUserProperties().put("name", "wb");
        super.modifyHandshake(sec, request, response);

    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListenerWithMetrics() {
        ServletListenerRegistrationBean<HttpSessionListener> listenerRegBean =
                new ServletListenerRegistrationBean<>();

        listenerRegBean.setListener(new HttpSessionConfig());
        return listenerRegBean;
    }

    /**
     * 握手协议前
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            String ID = request.getURI().toString().split("ID=")[1];
            log.info("current session id is:"+ID);
            attributes.put("WEBSOCKET_USERID",ID);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.info("coming webSocketInterceptor afterHandshake method...");
    }
}
