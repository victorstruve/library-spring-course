package ru.struve.LibraryAPI.dto;

import jakarta.persistence.Id;

public class ReaderDTO {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
