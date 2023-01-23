package ru.struve.LibraryAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.struve.LibraryAPI.models.Person;
import ru.struve.LibraryAPI.repositories.PeopleRepository;

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
    public List<Person> findALl(){
        return peopleRepository.findAll();
    }
    public Optional<Person> findByFullName(String fullName){
        return peopleRepository.findByFullName(fullName);
    }
    @Transactional
    public void addPerson(Person person){
        peopleRepository.save(person);
    }
    public Optional<Person> findById(int id){

        return peopleRepository.findById(id);
    }
    @Transactional
    public void edit(Person person, int id){
        person.setId(id);
        peopleRepository.save(person);
    }
    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }
}
