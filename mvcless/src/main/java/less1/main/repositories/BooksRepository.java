package less1.main.repositories;

import less1.main.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Optional<Book> getBooksByTitleAndYearAndAuthor(String title, int year, String author);
}
