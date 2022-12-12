package less1.main.dao;

import less1.main.models.Book;
import less1.main.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return  jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
    }
    public Book show(int id){
        return jdbcTemplate.query("select * from book where id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }
    public void save(Book book){
        jdbcTemplate.update("insert into book(title, author, year) values (?,?,?)", book.getTitle(),book.getAuthor(),book.getYear());
    }
    public void update(int id, Book updateBook){
        jdbcTemplate.update("update book set title=?, author=?,year=? where id=?", updateBook.getTitle(), updateBook.getAuthor(),
                updateBook.getYear(), id);
    }
    public void delete(int id){
        jdbcTemplate.update("delete from book where id = ?", id);
    }
    public void release(int id){
        jdbcTemplate.update("update book set person_id = NULL where id =?", id);
    }
    public void assign(int id, Person selectedPerson){
        jdbcTemplate.update("update book set person_id=? where id = ?", selectedPerson.getId(),id);
    }
    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("select person.* from person join book on person.id = book.person_id where book.id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
