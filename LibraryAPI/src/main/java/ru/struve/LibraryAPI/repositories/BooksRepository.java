package ru.struve.LibraryAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.struve.LibraryAPI.models.Book;
import ru.struve.LibraryAPI.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByTitleAndAuthorAndYear(String title, String author, int year);
    List<Book> findByOwner (Person person);
}
