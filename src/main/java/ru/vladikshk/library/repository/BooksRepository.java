package ru.vladikshk.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vladikshk.library.data.Author;
import ru.vladikshk.library.data.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    @Query("select distinct b from Book b left join fetch b.author left join fetch b.tags where b.id=:id")
    Optional<Book> findByIdWithAuthorAndTags(@Param("id") int id);

    @Query("select distinct b from Book b left join fetch b.tags where b.id=:id")
    Optional<Book> findByIdWithTags(@Param("id") int id);

    @Query("select distinct b from Book b left join fetch b.tags")
    List<Book> findAllWithTags();
}
