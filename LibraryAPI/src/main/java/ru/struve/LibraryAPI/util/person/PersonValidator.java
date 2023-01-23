package ru.struve.LibraryAPI.util.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.struve.LibraryAPI.models.Person;
import ru.struve.LibraryAPI.services.PeopleService;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (peopleService.findByFullName(person.getFullName()).isPresent())
            errors.rejectValue("fullName", "Уже есть читатель с таким ФИО");
        if (peopleService.findById(person.getId()).isEmpty() & person.getId()!=0) {
            errors.rejectValue("id", "Человека с таким id не существует");
            System.out.println(person.getId());
        }

    }



}
