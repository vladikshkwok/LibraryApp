package ru.vladikshk.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.vladikshk.library.dto.AuthorDTO;
import ru.vladikshk.library.dto.AuthorDetailsDTO;
import ru.vladikshk.library.dto.BookDTO;
import ru.vladikshk.library.dto.BookDetailsDTO;
import ru.vladikshk.library.mapper.AuthorMapper;
import ru.vladikshk.library.mapper.BookMapper;
import ru.vladikshk.library.service.BooksService;
import ru.vladikshk.library.util.EntityErrorResponse;
import ru.vladikshk.library.util.EntityNotModifiedException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final BookMapper bookMapper;

    @Autowired
    public BooksController(BooksService booksService, BookMapper bookMapper) {
        this.booksService = booksService;
        this.bookMapper = bookMapper;
    }

    @GetMapping()
    public List<BookDTO> getBooks() {
        return booksService.findAll().stream()
                .map(bookMapper::bookToBookDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDetailsDTO getBook(@PathVariable("id") int id) {
        return bookMapper.bookToBookDetailsDTO(booksService.findOne(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createBook(@RequestBody @Valid BookDTO bookDTO,
                                                   BindingResult bindingResult) {
        checkErrors(bindingResult);

        booksService.save(bookMapper.bookDTOToBook(bookDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBook(@PathVariable("id") int id,
                                                   @RequestBody @Valid BookDTO bookDTO,
                                                   BindingResult bindingResult) {
        checkErrors(bindingResult);

        booksService.update(id, bookMapper.bookDTOToBook(bookDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") int id) {
        booksService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void checkErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(error -> errorMsg.append(error.getField())
                    .append("-").append(error.getDefaultMessage()).append(";"));

            throw new EntityNotModifiedException(errorMsg.toString());
        }
    }


    @ExceptionHandler
    private ResponseEntity<EntityErrorResponse> handleException(EntityNotFoundException e) {
        EntityErrorResponse response = new EntityErrorResponse(
                "Книга с таким id не найдена",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<EntityErrorResponse> handleException(EntityNotModifiedException e) {
        EntityErrorResponse response = new EntityErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
