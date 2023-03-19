package ru.vladikshk.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.vladikshk.library.dto.AuthorDTO;
import ru.vladikshk.library.dto.AuthorDetailsDTO;
import ru.vladikshk.library.mapper.AuthorMapper;
import ru.vladikshk.library.service.AuthorsService;
import ru.vladikshk.library.service.BooksService;
import ru.vladikshk.library.util.EntityErrorResponse;
import ru.vladikshk.library.util.EntityNotModifiedException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    private final AuthorsService authorsService;
    private final BooksService booksService;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorsController(AuthorsService authorsService, BooksService booksService, AuthorMapper authorMapper) {
        this.authorsService = authorsService;
        this.booksService = booksService;
        this.authorMapper = authorMapper;
    }

    @GetMapping()
    public List<AuthorDTO> getAuthors() {
        return authorsService.findAll().stream()
                .map(authorMapper::authorToAuthorDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AuthorDetailsDTO getAuthor(@PathVariable("id") int id) {
        AuthorDetailsDTO author = authorMapper.authorToAuthorDetailsDTO(authorsService.findOne(id));
        return author;
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createAuthor(@RequestBody @Valid AuthorDTO authorDTO,
                                                   BindingResult bindingResult) {
        checkErrors(bindingResult);

        authorsService.save(authorMapper.authorDTOToAuthor(authorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateAuthor(@PathVariable("id") int id,
                                                   @RequestBody @Valid AuthorDTO authorDTO,
                                                   BindingResult bindingResult) {
        checkErrors(bindingResult);

        authorsService.update(id, authorMapper.authorDTOToAuthor(authorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable("id") int id) {
        authorsService.delete(id);

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
                "Автор с таким id не найден",
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