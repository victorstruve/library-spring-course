package ru.struve.hospital.soap.model;

import java.sql.Time;
import java.time.ZonedDateTime;

public class Schedule {
    private ZonedDateTime date;
    private int count;
    private int duration;

    public Schedule(ZonedDateTime date,  int count, int duration) {
        this.date = date;
        this.count = count;
        this.duration = duration;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setData(ZonedDateTime date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}