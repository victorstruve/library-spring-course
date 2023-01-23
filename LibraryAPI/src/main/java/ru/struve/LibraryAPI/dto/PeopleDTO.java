package ru.struve.LibraryAPI.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import ru.struve.LibraryAPI.models.Book;

import java.util.List;

public class PeopleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Имя читателя не должно быть пустым")
    @Size(min = 3, max = 40, message = "ФИО читателя должно быть от 3 до 40 символов")
    private String fullName;

    @NotNull
    @Min(value = 1900, message = "Дата рождения читателя не может быть меньше, чем 1900")
    @Max(value = 2023, message = "Дата рождения читателя не может быть больше, чем 2023")
    private int yearOfBirth;

    private String books;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Book book:books){
            stringBuilder.append("Id: ")
                    .append(book.getId())
                    .append(", Название: ")
                    .append(book.getTitle())
                    .append(", Год: ")
                    .append(book.getYear())
                    .append(" ");
        }
        if (books.isEmpty())
            this.books = "У читателя на руках нет книг";

        if (!books.isEmpty())
            this.books = stringBuilder.toString();
    }
}
