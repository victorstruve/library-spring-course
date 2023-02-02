package ru.struve.hospital.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class DoctorDTO {

    private int id;

    @NotEmpty(message = "ФИО врача не должно быть пустым")
    @Size(min = 3, max = 40, message = "ФИО врача не должно быть меньше 3 и превышать 40 символов")
    private String fullName;

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
}
