package ru.vladikshk.library.dto;


import lombok.Data;
import lombok.NonNull;
import ru.vladikshk.library.data.Author;
import ru.vladikshk.library.data.Tag;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class BookDetailsDTO {
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 3, max = 100, message = "Name should be between 3 and 100 characters")
    @NonNull
    private String name;

    private Author author;

    private List<Tag> tags;
}
