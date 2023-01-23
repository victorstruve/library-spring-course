package ru.struve.LibraryAPI.dto;



import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BookDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Название книги не может быть пустым")
    @Size(max = 30, message = "Название книги не должно быть больше 30")
    private String title;

    @NotEmpty(message = "ФИО автора не может быть пустым")
    @Size(max = 40, message = "ФИО не должно превышать 40 символов")
    private String author;

    @NotNull
    @Min(value = 1500,message = "Книга не может быть старше 1500 года")
    private int year;

    private String owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(PeopleDTO owner) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Id: ")
                .append(owner.getId())
                .append(", ФИО: ")
                .append(owner.getFullName());
        this.owner = stringBuilder.toString();
    }
}
