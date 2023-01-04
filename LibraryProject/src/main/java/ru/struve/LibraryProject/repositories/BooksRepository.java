package ru.struve.LibraryProject.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.struve.LibraryProject.models.Book;

import java.util.Optional;


@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Optional<Book> getBooksByTitleAndYearAndAuthor(String title, int year, String author);
}
