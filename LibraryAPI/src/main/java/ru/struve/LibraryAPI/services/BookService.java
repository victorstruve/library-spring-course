package ru.struve.LibraryAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.struve.LibraryAPI.models.Book;
import ru.struve.LibraryAPI.models.Person;
import ru.struve.LibraryAPI.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;
    private final PeopleService peopleService;

    @Autowired
    public BookService(BooksRepository booksRepository, PeopleService peopleService) {
        this.booksRepository = booksRepository;
        this.peopleService = peopleService;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }
    public Optional<Book> findByFullParams(String title, String author, int year){
        return booksRepository.findByTitleAndAuthorAndYear(title, author, year);
    }
    @Transactional
    public void add(Book book){
        booksRepository.save(book);
    }
    public Optional<Book> findByID(int id){
        return booksRepository.findById(id);
    }
    @Transactional
    public void edit(Book book, int id){
        book.setId(id);
        enrichBook(book);

        booksRepository.save(book);
    }
    @Transactional
    public void addReader(int bookId, int readerId){
        Book book = booksRepository.findById(bookId).get();
        book.setOwner(peopleService.findById(readerId).get());
        booksRepository.save(book);
    }
    @Transactional
    public void deleteReader(int id){
        Book book = booksRepository.findById(id).get();
        Person person = peopleService.findById(book.getOwner().getId()).get();
        person.setBooks(null);
        book.setOwner(null);
        peopleService.save(person);
        booksRepository.save(book);
    }
    @Transactional
    public void delete(int id){
        Person person = peopleService.findById(booksRepository.findById(id).get().getOwner().getId()).get();
        person.setBooks(null);
        peopleService.save(person);
        booksRepository.deleteById(id);
    }
    public List<Book> findByOwner(int ownerId){
        Person person = new Person();
        person.setId(ownerId);
        return booksRepository.findByOwner(person);
    }
    public void enrichBook(Book book){
        book.setOwner(peopleService.findById(book.getOwner().getId()).get());
    }
}
