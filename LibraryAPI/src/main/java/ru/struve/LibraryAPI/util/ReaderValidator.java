package ru.struve.LibraryAPI.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.struve.LibraryAPI.dto.ReaderDTO;
import ru.struve.LibraryAPI.services.PeopleService;

@Component
public class ReaderValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public ReaderValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ReaderDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReaderDTO readerDTO = (ReaderDTO) target;
        if(peopleService.findById(readerDTO.getId()).isEmpty() & readerDTO.getId()!=0){
            errors.rejectValue("id", "Человека с таким id не существует");
        }
    }
}
