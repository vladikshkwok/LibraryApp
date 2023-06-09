package ru.vladikshk.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.vladikshk.library.data.TagSummary;
import ru.vladikshk.library.dto.TagDTO;
import ru.vladikshk.library.dto.TagDetailsDTO;
import ru.vladikshk.library.mapper.TagMapper;
import ru.vladikshk.library.service.TagsService;
import ru.vladikshk.library.util.EntityErrorResponse;
import ru.vladikshk.library.util.EntityNotModifiedException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tags")
public class TagsController {

    private final TagsService tagsService;
    private final TagMapper tagMapper;

    @Autowired
    public TagsController(TagsService tagsService, TagMapper tagMapper) {
        this.tagsService = tagsService;
        this.tagMapper = tagMapper;
    }

    @GetMapping()
    public List<TagDTO> getTags() {
        return tagsService.findAll();
    }

    @GetMapping("/{id}")
    public TagDetailsDTO getTag(@PathVariable("id") int id) {
        return tagsService.findOne(id);
    }

    @GetMapping("/summary")
    public List<TagSummary> getTagsSummary() {
        return tagsService.getTagsSummary();
    }

    @PostMapping()
    public ResponseEntity<TagDetailsDTO> createTag(@RequestBody @Valid TagDTO tagDTO,
                                                   BindingResult bindingResult) {
        checkErrors(bindingResult);
        return ResponseEntity.ok(tagsService.save(tagDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDetailsDTO> updateTag(@PathVariable("id") int id,
                                                   @RequestBody @Valid TagDetailsDTO tagDetailsDTO,
                                                   BindingResult bindingResult) {
        checkErrors(bindingResult);
        return ResponseEntity.ok(tagsService.update(id, tagDetailsDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") int id) {
        tagsService.delete(id);

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
