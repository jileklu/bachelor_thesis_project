package com.jileklu2.bakalarska_prace_app.handlers;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class DateTimeHandler {
    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate).collect(Collectors.toList());
    }

    public static List<LocalTime> getTimesBetween(LocalTime startTime, LocalTime endTime, int interval) {
        List<LocalTime> timesBetween = new ArrayList<>();

        if(Duration.between(startTime, endTime).getSeconds() / 60 < interval) {
            timesBetween.add(startTime);
            return timesBetween;
        }

        LocalTime tmpTime = startTime;
        while(tmpTime.isBefore(endTime)) {
            timesBetween.add(tmpTime);
            tmpTime = tmpTime.plus(interval, ChronoUnit.MINUTES);
        }

        return timesBetween;
    }

    public static List<LocalDate> filterDatesByDays(List<LocalDate> dates, HashSet<DayOfWeek> allowedDays) {
        List<LocalDate> newDatesList = new ArrayList<>();

        for(LocalDate date : dates) {
            if(allowedDays.contains(date.getDayOfWeek()))
                newDatesList.add(date);
        }

        return newDatesList;
    }

    public static HashSet<LocalDateTime> combineTimesDates(List<LocalDate> dates, List<LocalTime> times) {
        HashSet<LocalDateTime> stamps = new HashSet<>();

        for(LocalDate date : dates) {
            for(LocalTime time : times) {
                stamps.add(LocalDateTime.of(date,time));
            }
        }

        return stamps;
    }
}
