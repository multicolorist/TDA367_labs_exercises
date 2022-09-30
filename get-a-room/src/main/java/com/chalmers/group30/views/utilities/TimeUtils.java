package com.chalmers.group30.views.utilities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class TimeUtils {
    public static Instant localTimeToInstant(LocalTime time, LocalDate date) {
        return time.atDate(date).atZone(ZoneId.systemDefault()).toInstant();
    }
}
