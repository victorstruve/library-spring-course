package ru.struve.LibraryProject.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Название не должно быть пустое")
    @Size(max=30, message = "Название должно быть меньше 30 символов")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Имя автора не может быть пустым")
    @Size(max=30,message = "Имя не должно превышать 30 символов")
    @Column(name = "author")
    private String author;

    @Min(value = 1500, message = "Книга не может быть страше 1500 года")
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book(){

    }
    public Book(String title, String author, int year){
        this.title=title;
        this.author=author;
        this.year=year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
