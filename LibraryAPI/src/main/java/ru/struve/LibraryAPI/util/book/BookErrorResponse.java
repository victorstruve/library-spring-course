package ru.struve.LibraryAPI.util.book;

public class BookErrorResponse {

    private String error;

    private long timestamp;

    public BookErrorResponse(String error, long timestamp) {
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
