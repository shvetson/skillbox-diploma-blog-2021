package ru.shvets.blog.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;

@Service
public class TimeUtils {
    public long getSecondsOffSet() {
        ZoneId z = ZoneId.systemDefault();
        return z.getRules().getOffset(Instant.now()).getTotalSeconds();
    }
}