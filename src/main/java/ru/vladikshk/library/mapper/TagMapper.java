package ru.vladikshk.library.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.vladikshk.library.data.Book;
import ru.vladikshk.library.data.Tag;
import ru.vladikshk.library.dto.TagDTO;
import ru.vladikshk.library.dto.TagDetailsDTO;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDTO tagToTagDTO(Tag tag);

    TagDetailsDTO tagToTagDetailsDTO(Tag tag);

    Tag tagDTOToTag(TagDTO tagDTO);

    Tag tagDetailsDTOToTag(TagDetailsDTO tagDetailsDTO);

}
