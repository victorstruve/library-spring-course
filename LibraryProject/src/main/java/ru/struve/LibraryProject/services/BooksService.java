package ru.struve.LibraryProject.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.struve.LibraryProject.models.Book;
import ru.struve.LibraryProject.models.Person;
import ru.struve.LibraryProject.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }
    public List<Book> findAll(){
        return booksRepository.findAll();
    }
    public Book show(int id){
        return booksRepository.findById(id).orElse(null);
    }
    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }
    public Optional<Book> getBookByTitleAndYear(String title, int year, String author){
        return booksRepository.getBooksByTitleAndYearAndAuthor(title, year, author);
    }
    @Transactional
    public void update(int id, Book updatedBook){
        Book bookToBeUpdated = booksRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        booksRepository.save(updatedBook);

    }
    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }
    public Person getBookOwner(int id){
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }
    @Transactional
    public void release(int id){
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                }
        );
    }
    @Transactional
    public void assign(int id, Person selectedPerson){
        booksRepository.findById(id).ifPresent(
                book -> book.setOwner(selectedPerson)
        );
    }
}
