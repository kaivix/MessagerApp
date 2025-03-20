package com.kaivix.chatservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import java.util.Map;
@Component
@Slf4j
public class JwtHandShakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider tokenProvider;

    public JwtHandShakeInterceptor(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String authorizationHeader = request.getHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            System.out.println("Получен токен: {}" + token);
            if (tokenProvider.validateToken(token)) {
                String username = tokenProvider.getUsername(token);
                attributes.put("username", username);
                return true;
            }
        }
        System.out.println("Токен недействителен или отсутствует");
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}
