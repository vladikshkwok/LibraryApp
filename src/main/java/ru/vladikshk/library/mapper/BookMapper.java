package ru.vladikshk.library.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.vladikshk.library.data.Book;
import ru.vladikshk.library.dto.BookDTO;
import ru.vladikshk.library.dto.BookDetailsDTO;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO bookToBookDTO(Book book);

    BookDetailsDTO bookToBookDetailsDTO(Book book);

    Book bookDTOToBook(BookDTO bookDTO);

    Book bookDetailsDTOToBook(BookDetailsDTO bookDetailsDTO);

}
