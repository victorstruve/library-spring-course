package ru.struve.hospital.rest.dto;

import jakarta.validation.constraints.NotNull;

public class RecordFormDTO {
    @NotNull
    private int doctorId;
    @NotNull
    private int recordId;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

}
