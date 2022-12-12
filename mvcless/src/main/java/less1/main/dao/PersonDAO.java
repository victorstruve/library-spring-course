package less1.main.dao;

import less1.main.models.Book;
import less1.main.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index(){
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> getPersonByFullName(String name){
        return jdbcTemplate.query("select * from person where full_name=?", new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public Person show(int id){
       return  jdbcTemplate.query("select * from person where id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
               .stream().findAny().orElse(null);
    }

    public void save(Person person){
        jdbcTemplate.update("insert into person(full_name, year_of_birth) values(?,?)", person.getFullName(),person.getYearOfBirth());
    }
    public void update(int id, Person updatePerson){
        jdbcTemplate.update("update person set full_name=?, year_of_birth=? where id=?", updatePerson.getFullName(),
                updatePerson.getYearOfBirth(), id);
    }
    public void delete(int id){
        jdbcTemplate.update("delete from person where id=?", id);
    }
    public List<Book> getBookById(int id){
        return jdbcTemplate.query("select * from book where person_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }

}

