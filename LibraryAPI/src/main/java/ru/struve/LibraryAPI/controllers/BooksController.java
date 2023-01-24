package ru.struve.LibraryAPI.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.struve.LibraryAPI.dto.BookDTO;
import ru.struve.LibraryAPI.dto.BookResponse;
import ru.struve.LibraryAPI.dto.ReaderDTO;
import ru.struve.LibraryAPI.models.Book;
import ru.struve.LibraryAPI.services.BookService;
import ru.struve.LibraryAPI.util.*;
import ru.struve.LibraryAPI.util.book.BookErrorResponse;
import ru.struve.LibraryAPI.util.book.BookValidator;
import ru.struve.LibraryAPI.util.person.PersonValidator;

import java.util.stream.Collectors;

import static ru.struve.LibraryAPI.util.ErrorUtil.returnErrorsToClient;

@RestController
@RequestMapping("/books")
public class BooksController {
    private final PersonValidator personValidator;
    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final BookValidator bookValidator;
    private final ReaderValidator readerValidator;

    @Autowired
    public BooksController(PersonValidator personValidator, BookService bookService, ModelMapper modelMapper, BookValidator bookValidator, ReaderValidator readerValidator) {
        this.personValidator = personValidator;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.bookValidator = bookValidator;
        this.readerValidator = readerValidator;
    }

    @GetMapping()
    public BookResponse getBooks(){
        return new BookResponse(bookService.findAll().stream().map(this::convertToBookDTO)
                .collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid BookDTO bookDTO,
                                          BindingResult bindingResult){
         Book book = convertToBook(bookDTO);
         bookValidator.validate(book,bindingResult);
         if (bindingResult.hasErrors()){
             returnErrorsToClient(bindingResult);
         }
         bookService.add(book);
         return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable("id") int id){
        BookResponse book = new BookResponse(bookService.findByID(id).stream().map(this::convertToBookDTO)
                .collect(Collectors.toList()));
        if (book.getBooks().isEmpty()){
            book.setStatus("Данная книга не существует");
            return book;
        }
        book.setStatus("Книга существует");
        return book;
    }
    @PatchMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> edit(@RequestBody @Valid BookDTO bookDTO,
                                           @PathVariable("id") int id,
                                           BindingResult bindingResult){
        Book bookToEdit = convertToBook(bookDTO);
        bookToEdit.setId(id);
        bookValidator.validate(bookToEdit, bindingResult);
        if (bindingResult.hasErrors()){
            returnErrorsToClient(bindingResult);
        }
        bookService.edit(bookToEdit, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PatchMapping("/{id}/setReader")
    public ResponseEntity<HttpStatus> setReader(@RequestBody ReaderDTO readerDTO,
                                                @PathVariable("id") int id,
                                                BindingResult bindingResult){
        readerValidator.validate(readerDTO, bindingResult);
        if (bindingResult.hasErrors()){
            returnErrorsToClient(bindingResult);
        }
        if (bookService.findByID(id).get().getOwner() != null)
            return ResponseEntity.ok(HttpStatus.BAD_GATEWAY);
        bookService.addReader(id,readerDTO.getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/{id}/clearReader")
    public ResponseEntity<HttpStatus> clearReader(@PathVariable("id") int id){
        if(bookService.findByID(id).isEmpty()){
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
        bookService.deleteReader(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") int id){
        if(bookService.findByID(id).isEmpty()){
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
        bookService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handleException(ClientException err){
        BookErrorResponse response = new BookErrorResponse(
                err.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }


    public BookDTO convertToBookDTO(Book book){
        return modelMapper.map(book, BookDTO.class);
    }
    public Book convertToBook(BookDTO bookDTO){
        return modelMapper.map(bookDTO, Book.class);
    }

}
