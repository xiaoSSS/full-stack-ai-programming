package com.xiaoss.starter.utils.lang;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class TimeUtils {

    private TimeUtils() {
    }

    public static ZonedDateTime now(String zoneId) {
        return ZonedDateTime.now(ZoneId.of(zoneId));
    }

    public static LocalDateTime nowLocal() {
        return LocalDateTime.now();
    }
}
