package com.http.demo.session;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionContext {

    private static HttpSessionContext instance;

    public static HttpSessionContext getInstance() {
        if (instance == null) {
            return instance = new HttpSessionContext();
        }
        return instance;
    }

    private final Map<String, HttpSession> map = new ConcurrentHashMap<>();

    private HttpSessionContext() {

    }

    public void add(HttpSession httpSession) {

        map.put(httpSession.getId(), httpSession);
    }

    public void remove(String sessionId) {
        map.remove(sessionId);
    }

    public HttpSession get(String sessionId) {
        return map.get(sessionId);
    }
}
