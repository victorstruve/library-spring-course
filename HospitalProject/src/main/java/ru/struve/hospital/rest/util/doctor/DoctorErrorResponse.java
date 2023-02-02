package ru.struve.hospital.rest.util.doctor;

public class DoctorErrorResponse {
    private String error;
    private long timestamp;

    public DoctorErrorResponse(String error, long timestamp) {
        this.error = error;
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
