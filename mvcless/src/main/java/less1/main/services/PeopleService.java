package less1.main.services;


import less1.main.models.Book;
import less1.main.models.Person;
import less1.main.repositories.BooksRepository;
import less1.main.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    public List<Person> findAll(){
        return peopleRepository.findAll();
    }
    public Optional<Person> getPersonByFullName(String name){
        return peopleRepository.findByFullName(name);
    }
    public Person show(int id){
        return peopleRepository.findById(id).orElse(null);
    }
    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }
    public List<Book> getBooksByPersonId(int id){
        Optional<Person> person = peopleRepository.findById(id);
        Hibernate.initialize(person.get().getBooks());
        return person.get().getBooks();
    }
}
