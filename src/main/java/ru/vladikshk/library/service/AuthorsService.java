package ru.vladikshk.library.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vladikshk.library.data.Author;
import ru.vladikshk.library.repository.AuthorsRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthorsService {

    private final AuthorsRepository authorsRepository;

    @Autowired
    public AuthorsService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public List<Author> findAll() {
        return authorsRepository.findAll();
    }

    public Author findOne(int id) {
        Optional<Author> author = authorsRepository.findById(id);
        author.ifPresent(a -> Hibernate.initialize(a.getBooks()));
        return author.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void save(Author author) {
        authorsRepository.save(author);
    }

    @Transactional
    public void update(int id, Author updatedAuthor) {
        updatedAuthor.setId(id);
        authorsRepository.save(updatedAuthor);
    }

    @Transactional
    public void delete(int id) {
        authorsRepository.deleteById(id);
    }
}
