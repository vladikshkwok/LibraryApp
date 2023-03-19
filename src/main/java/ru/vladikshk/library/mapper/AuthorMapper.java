package ru.vladikshk.library.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.vladikshk.library.data.Author;
import ru.vladikshk.library.dto.AuthorDTO;
import ru.vladikshk.library.dto.AuthorDetailsDTO;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO authorToAuthorDTO(Author author);
    AuthorDetailsDTO authorToAuthorDetailsDTO(Author author);
    Author authorDTOToAuthor(AuthorDTO authorDTO);
    Author authorDetailsDTOToAuthor(AuthorDetailsDTO authorDetailsDTO);

}
