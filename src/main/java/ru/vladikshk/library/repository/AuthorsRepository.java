package ru.vladikshk.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vladikshk.library.data.Author;
import ru.vladikshk.library.dto.AuthorDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Integer> {

    @Query("select distinct a from Author a left join fetch a.books where a.id=:id")
    Optional<Author> findByIdWithBooks(@Param("id") int id);

    Optional<Author> findByUsername(String username);

    @Query("SELECT DISTINCT a FROM Author a JOIN a.books b JOIN b.tags t WHERE t.name = :tagName")
    List<Author> findAllByTag(@Param("tagName") String tagName);
}
