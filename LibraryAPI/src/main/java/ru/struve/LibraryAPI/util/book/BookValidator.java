package ru.struve.LibraryAPI.util.book;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.struve.LibraryAPI.models.Book;
import ru.struve.LibraryAPI.services.BookService;
import ru.struve.LibraryAPI.services.PeopleService;

@Component
public class BookValidator implements Validator {
    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if(bookService.findByFullParams(book.getTitle(), book.getAuthor(), book.getYear()).isPresent()){
            errors.rejectValue("title", "Книга с такими параметрами уже существует в библиотеке");
        }
        if (bookService.findByID(book.getId()).isEmpty() & book.getId()!=0) {
            errors.rejectValue("id", "Книги с таким id не существует");
            System.out.println(book.getId());
        }
    }
}
