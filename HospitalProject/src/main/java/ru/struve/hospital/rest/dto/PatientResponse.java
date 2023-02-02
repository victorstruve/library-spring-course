package ru.struve.hospital.rest.dto;


import java.util.List;

public class PatientResponse {
    private PatientDTO patient;

    private List<String> patientRecords;

    public PatientResponse(PatientDTO patient, List<String> patientRecords) {
        this.patient = patient;
        this.patientRecords = patientRecords;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public List<String> getPatientRecords() {
        return patientRecords;
    }

    public void setPatientRecords(List<String> patientRecords) {
        this.patientRecords = patientRecords;
    }
}
