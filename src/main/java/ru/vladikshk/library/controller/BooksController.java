package ru.vladikshk.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.vladikshk.library.dto.BookDTO;
import ru.vladikshk.library.dto.BookDetailsDTO;
import ru.vladikshk.library.mapper.BookMapper;
import ru.vladikshk.library.service.BooksService;
import ru.vladikshk.library.util.EntityErrorResponse;
import ru.vladikshk.library.util.EntityNotModifiedException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;

    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping()
    public List<BookDTO> getBooks() {
        return booksService.findAll();
    }

    @GetMapping("/{id}")
    public BookDetailsDTO getBook(@PathVariable("id") int id) {
        return booksService.findOne(id);
    }

    @PostMapping()
    public ResponseEntity<BookDetailsDTO> createBook(@RequestBody @Valid BookDTO bookDTO,
                                                   BindingResult bindingResult) {
        checkErrors(bindingResult);
        return ResponseEntity.ok(booksService.save(bookDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDetailsDTO> updateBook(@PathVariable("id") int id,
                                                     @RequestBody @Valid BookDetailsDTO bookDetailsDTO,
                                                     BindingResult bindingResult) {
        checkErrors(bindingResult);
        return ResponseEntity.ok(booksService.update(id, bookDetailsDTO));
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
