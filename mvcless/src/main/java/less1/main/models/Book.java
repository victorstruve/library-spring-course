package less1.main.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int id;
    @NotEmpty(message = "Название не должно быть пустое")
    @Size(max=30, message = "Название должно быть меньше 30 символов")
    private String title;

    @NotEmpty(message = "Имя автора не может быть пустым")
    @Size(max=30,message = "Имя не должно превышать 30 символов")
    private String author;

    @Min(value = 1500, message = "Книга не может быть страше 1500 года")
    private int year;


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

    public void setYear(int year) {
        this.year = year;
    }
}
