package ru.vladikshk.library.dto;

import lombok.Data;
import lombok.NonNull;
import ru.vladikshk.library.data.Book;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AuthorDetailsDTO {
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 3, max = 100, message = "Name should be between 3 and 100 characters")
    @NonNull
    String name;

    List<Book> books;
}
