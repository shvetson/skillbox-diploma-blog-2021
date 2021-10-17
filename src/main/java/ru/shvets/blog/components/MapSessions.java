package ru.shvets.blog.components;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class MapSessions {
    private final Map<String, Long> map = new HashMap<>();

    //Внесение записи в лист сессий
    public void addData(String sessionId, Long userId) {
        if (isSession(sessionId)) {
            map.remove(sessionId);
        }

        if (!isData(sessionId, userId)) {
            map.put(sessionId, userId);
        }
    }

    //Получить id  сессии по id пользователя
    public String getSessionId(Long userId) {
        for (Map.Entry<String, Long> item : map.entrySet()) {
            if (item.getValue().equals(userId)) {
                return item.getKey();
            }
        }
        return null;
    }

    //Получить id пользователя по id сессии
    public Long getUserId(String sessionId) {
        for (Map.Entry<String, Long> item : map.entrySet()) {
            if (item.getKey().equals(sessionId)) {
                return item.getValue();
            }
        }
        return null;
    }

    //Проверка есть ли сессия в листе сессий
    public boolean isSession(String sessionId) {
        for (Map.Entry<String, Long> item : map.entrySet()) {
            if (item.getKey().equals(sessionId)) {
                return true;
            }
        }
        return false;
    }

    //Проверка есть ли пользователь в листе сессии
    public boolean isUser(Long userId) {
        for (Map.Entry<String, Long> item : map.entrySet()) {
            if (item.getValue().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    //Проверка есть ли запись с id сессии и id пользователя в листе сессий
    public boolean isData(String sessionID, Long userId) {
        for (Map.Entry<String, Long> item : map.entrySet()) {
            if ((item.getKey().equals(sessionID)) & (item.getValue().equals(userId))) {
                return true;
            }
        }
        return false;
    }

    //Проверка есть ли зарегистрированные пользователи
    public boolean isEmpty(){
        return map.size() == 0;
    }
}