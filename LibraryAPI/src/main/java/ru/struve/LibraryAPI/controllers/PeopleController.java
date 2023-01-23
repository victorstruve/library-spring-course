package ru.struve.LibraryAPI.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.struve.LibraryAPI.dto.PeopleDTO;
import ru.struve.LibraryAPI.dto.PeopleResponse;
import ru.struve.LibraryAPI.models.Book;
import ru.struve.LibraryAPI.models.Person;
import ru.struve.LibraryAPI.services.BookService;
import ru.struve.LibraryAPI.services.PeopleService;
import ru.struve.LibraryAPI.util.person.PeopleErrorResponse;
import ru.struve.LibraryAPI.util.ClientException;
import ru.struve.LibraryAPI.util.person.PersonValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.struve.LibraryAPI.util.ErrorUtil.returnErrorsToClient;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private final PersonValidator personValidator;
    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PersonValidator personValidator, BookService bookService, ModelMapper modelMapper, PeopleService peopleService) {
        this.personValidator = personValidator;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public PeopleResponse getPeople(){
        return new PeopleResponse(peopleService.findALl().stream().map(this::convertToPeopleDTO)
                .collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid PeopleDTO peopleDTO,
                                          BindingResult bindingResult){
        Person personToAdd = convertTOPerson(peopleDTO);
        personValidator.validate(personToAdd,bindingResult);
        if(bindingResult.hasErrors()){
            returnErrorsToClient(bindingResult);
        }
        peopleService.addPerson(personToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public PeopleResponse getPerson(@PathVariable("id") int id){
//        Optional<Person> personForWhat = peopleService.findById(id);
//        personForWhat.get().setBooks(bookService.findByOwner(id));
        PeopleResponse person = new PeopleResponse(peopleService.findById(id).stream().map(this::convertToPeopleDTO)
                .collect(Collectors.toList()));
        if (person.getPeople().isEmpty()){
            person.setStatus("Человека с данным id не существует");
            return person;
        }
        person.getPeople().get(0).setBooks(bookService.findByOwner(id));
        person.setStatus("Подтвержденный");
        return person;
    }
    @PatchMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> edit(@RequestBody @Valid PeopleDTO peopleDTO,
                                           @PathVariable("id") int id,
                                           BindingResult bindingResult){
        Person personToEdit = convertTOPerson(peopleDTO);
        personToEdit.setId(id);
        personValidator.validate(personToEdit, bindingResult);
        if(bindingResult.hasErrors()){
            returnErrorsToClient(bindingResult);
        }
        peopleService.edit(personToEdit,id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/{personId}/dropBook/{bookId}")
    public ResponseEntity<HttpStatus> dropBook(@PathVariable("personId") int personId,
                                               @PathVariable("bookId") int bookId){
        if(peopleService.findById(personId).isEmpty()){
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
        if(bookService.findByID(bookId).isEmpty()){
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
        bookService.deleteReader(bookId);
        return ResponseEntity.ok(HttpStatus.OK);
    }



    @ExceptionHandler
    private ResponseEntity<PeopleErrorResponse> handleException(ClientException err){
        PeopleErrorResponse response = new PeopleErrorResponse(
                err.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    private PeopleDTO convertToPeopleDTO(Person person){
        return modelMapper.map(person,PeopleDTO.class);
    }
    private Person convertTOPerson(PeopleDTO peopleDTO){
        return modelMapper.map(peopleDTO,Person.class);
    }
}
