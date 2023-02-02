package ru.struve.hospital.rest.dto;

import jakarta.validation.constraints.NotNull;

public class SlotsResponse {
    @NotNull(message = "id не может быть равно 0")
    private int patientId;


    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
