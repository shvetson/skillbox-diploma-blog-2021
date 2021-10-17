package ru.shvets.blog.utils;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TimeUtils {
    public long getSecondsOffSet() {
        ZoneId z = ZoneId.systemDefault();
        return z.getRules().getOffset(Instant.now()).getTotalSeconds();
    }

    public Date checkAndCorrectTimestamp(Timestamp timestamp) {
        if (timestamp.getTime() < new Date().getTime()/1000){
            return new Date();
        } else {
            return new Date(timestamp.getTime()*1000);
        }
    }
}