package ru.struve.hospital.rest.dto;


import jakarta.validation.constraints.NotNull;

public class DoctorSearchForm {

    @NotNull
    private int doctorId;

    @NotNull
    private int year;

    @NotNull
    private int month;

    @NotNull
    private int day;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
