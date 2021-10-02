package ru.shvets.blog.components;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class MapSessions {
    private final Map<String, Long> map = new HashMap<>();

    public void addData(String sessionId, Long userId) {
        if (!isData(sessionId, userId)) {
            map.put(sessionId, userId);
        }
    }

    public String getSessionId(Long userId) {
        for (Map.Entry<String, Long> item : map.entrySet()) {
            if (item.getValue().equals(userId)) {
                return item.getKey();
            }
        }
        return null;
    }

    public Long getUserId(String sessionId) {
        for (Map.Entry<String, Long> item : map.entrySet()) {
            if (item.getKey().equals(sessionId)) {
                return item.getValue();
            }
        }
        return null;
    }

    public boolean isSession(String sessionId) {
        for (Map.Entry<String, Long> item : map.entrySet()) {
            if (item.getKey().equals(sessionId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUser(Long userId) {
        for (Map.Entry<String, Long> item : map.entrySet()) {
            if (item.getValue().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isData(String sessionID, Long userId) {
        for (Map.Entry<String, Long> item : map.entrySet()) {
            if ((item.getKey().equals(sessionID)) & (item.getValue().equals(userId))) {
                return true;
            }
        }
        return false;
    }


}
