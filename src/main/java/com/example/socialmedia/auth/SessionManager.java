package com.example.socialmedia.auth;

import javax.swing.plaf.PanelUI;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    // 模擬存在記憶體的 token → userId
    private static final Map<String, Long> tokenStore = new ConcurrentHashMap<>();

    // 登入成功後產生 token
    public static String generateToken(Long userId){
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, userId);
        return token;
    }

    // 驗證 token 是否存在
    public static boolean isValidToken(String token){
        return tokenStore.containsKey(token);
    }

    // 根據 token 取得 userId（之後發文用）
    public static Long getUserId(String token) {
        return tokenStore.get(token);
    }


}
