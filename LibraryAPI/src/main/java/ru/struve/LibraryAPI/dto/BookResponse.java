package ru.struve.LibraryAPI.dto;

import ru.struve.LibraryAPI.models.Book;

import java.util.List;

public class BookResponse {
    private List<BookDTO> books;
    private String status;

    public BookResponse(List<BookDTO> books) {
        this.books = books;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
