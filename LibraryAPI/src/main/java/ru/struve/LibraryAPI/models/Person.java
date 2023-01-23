package ru.struve.LibraryAPI.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.aop.target.LazyInitTargetSource;

import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Имя читателя не должно быть пустым")
    @Size(min = 3, max = 40, message = "ФИО читателя должно быть от 3 до 40 символов")
    @Column(name = "full_name")
    private String fullName;

    @NotNull
    @Min(value = 1900, message = "Дата рождения читателя не может быть меньше, чем 1900")
    @Max(value = 2023, message = "Дата рождения читателя не может быть больше, чем 2023")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(){}

    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

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

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
