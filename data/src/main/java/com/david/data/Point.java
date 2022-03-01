package com.david.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.UUID;

public class Point implements Comparable<Point>, Serializable {
    private final String id;
    private final LocalDateTime dateAndTime;

    public Point(LocalDateTime dateAndTime) {
        id = UUID.randomUUID().toString().replace("-", "");
        this.dateAndTime = dateAndTime;
    }

    public int getDay() {
        return dateAndTime.getDayOfMonth();
    }

    public int getMonth() {
        return dateAndTime.getMonthValue();
    }

    public int getYear() {
        return dateAndTime.getYear();
    }

    public int getHour() {
        return dateAndTime.getHour();
    }

    public int getMinute() {
        return dateAndTime.getMinute();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    @Override
    public String toString() {
        return dateAndTime.toString();
    }

    public static Point getRandomPoint() {
        return new Point(LocalDateTime.of(LocalDate.of(CustomMethods.randomNumber(2020, 2021), CustomMethods.randomNumber(1, 12), CustomMethods.randomNumber(1, 27)),
                LocalTime.of(CustomMethods.randomNumber(0, 23), CustomMethods.randomNumber(0, 59))));
    }

    @Override
    public int compareTo(Point point) {
        return dateAndTime.compareTo(point.dateAndTime);
    }
}
