package ru.struve.hospital.rest.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class PatientDTO {


    private int id;

    @NotEmpty(message = "ФИО пациента не должно быть пустым")
    @Size(min = 3, max = 40, message = "ФИО пациента не должно быть меньше 3 и превышать 40 символов")
    private String fullName;

    @NotNull(message = "Введите дату рождения")
    private LocalDate dateOfBirth;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        LocalDate date = ZonedDateTime.ofInstant(dateOfBirth.toInstant(), ZoneId.systemDefault()).toLocalDate();
        this.dateOfBirth = date;
    }
}
