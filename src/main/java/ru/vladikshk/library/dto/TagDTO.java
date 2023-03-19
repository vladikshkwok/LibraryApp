package ru.vladikshk.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vladikshk.library.data.Book;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 3, max = 100, message = "Name should be between 3 and 100 characters")
    String name;
}
