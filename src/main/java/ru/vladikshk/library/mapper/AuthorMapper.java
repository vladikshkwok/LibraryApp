package ru.vladikshk.library.mapper;

import org.mapstruct.Mapper;
import ru.vladikshk.library.data.Author;
import ru.vladikshk.library.dto.AuthorDTO;
import ru.vladikshk.library.dto.AuthorDetailsDTO;

@Mapper
public interface AuthorMapper {
    AuthorDTO authorToAuthorDTO(Author author);
    AuthorDetailsDTO authorToAuthorDetailsDTO(Author author);
    Author authorDTOToAuthor(AuthorDTO authorDTO);
    Author authorDetailsDTOToAuthor(AuthorDetailsDTO authorDetailsDTO);

}
