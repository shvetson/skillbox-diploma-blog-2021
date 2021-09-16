package ru.shvets.blog.utils;

import java.time.Instant;
import java.time.ZoneId;

public class TimeUtils {
    public int getSecondsOffSet() {
        ZoneId z = ZoneId.systemDefault();
        return z.getRules().getOffset(Instant.now()).getTotalSeconds();
    }
}