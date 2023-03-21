package ru.vladikshk.library.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vladikshk.library.data.Author;
import ru.vladikshk.library.dto.AuthorDTO;
import ru.vladikshk.library.dto.AuthorDetailsDTO;
import ru.vladikshk.library.mapper.AuthorMapper;
import ru.vladikshk.library.mapper.BookMapper;
import ru.vladikshk.library.repository.AuthorsRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AuthorsService {

    private final AuthorsRepository authorsRepository;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    @Autowired
    public AuthorsService(AuthorsRepository authorsRepository, AuthorMapper authorMapper, BookMapper bookMapper) {
        this.authorsRepository = authorsRepository;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
    }

    public List<AuthorDTO> findAll() {
        return authorsRepository.findAll().stream()
                .map(authorMapper::authorToAuthorDTO)
                .collect(Collectors.toList());
    }

    public AuthorDetailsDTO findOne(int id) {
        return authorsRepository.findById(id)
                .map(a -> {
                    Hibernate.initialize(a.getBooks());
                    return authorMapper.authorToAuthorDetailsDTO(a);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public AuthorDetailsDTO save(AuthorDTO author) {
        return authorMapper.authorToAuthorDetailsDTO(
                authorsRepository.save(authorMapper.authorDTOToAuthor(author))
        );
    }

    @Transactional
    public AuthorDetailsDTO update(int id, AuthorDTO updatedAuthor) {
        Author author = authorMapper.authorDTOToAuthor(updatedAuthor);
        author.setId(id);
        return authorMapper.authorToAuthorDetailsDTO(authorsRepository.save(author));
    }

    @Transactional
    public void delete(int id) {
        authorsRepository.deleteById(id);
    }
}
