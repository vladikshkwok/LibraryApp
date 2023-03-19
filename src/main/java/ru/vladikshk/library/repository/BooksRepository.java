package ru.vladikshk.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vladikshk.library.data.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}
