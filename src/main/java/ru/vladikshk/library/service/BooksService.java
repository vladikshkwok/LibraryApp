package ru.vladikshk.library.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vladikshk.library.data.Book;
import ru.vladikshk.library.dto.BookDTO;
import ru.vladikshk.library.dto.BookDetailsDTO;
import ru.vladikshk.library.mapper.BookMapper;
import ru.vladikshk.library.repository.BooksRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BooksService(BooksRepository booksRepository, BookMapper bookMapper) {
        this.booksRepository = booksRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDTO> findAll() {
        return booksRepository.findAll().stream()
                .map(bookMapper::bookToBookDTO)
                .collect(Collectors.toList());
    }

    public BookDetailsDTO findOne(int id) {
        return booksRepository.findByIdWithAuthorAndTags(id)
                .map(bookMapper::bookToBookDetailsDTO)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public BookDetailsDTO save(BookDTO book) {
        return bookMapper.bookToBookDetailsDTO(booksRepository.save(bookMapper.bookDTOToBook(book)));
    }

    @Transactional
    public BookDetailsDTO update(int id, BookDetailsDTO updatedBook) {
        Book book = bookMapper.bookDetailsDTOToBook(updatedBook);
        book.setId(id);
        return bookMapper.bookToBookDetailsDTO(booksRepository.save(book));
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

//    @Transactional
//    public BookDetailsDTO assignAuthor(int bookId, int )

}
