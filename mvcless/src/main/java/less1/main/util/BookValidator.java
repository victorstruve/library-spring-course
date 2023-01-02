package less1.main.util;

import less1.main.models.Book;
import less1.main.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService){
        this.booksService=booksService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        if (booksService.getBookByTitleAndYear(book.getTitle(), book.getYear(), book.getAuthor()).isPresent())
            errors.rejectValue("title","","Данная книга уже существует в библиотеке");
    }
}
