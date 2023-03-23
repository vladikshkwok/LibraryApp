package ru.vladikshk.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vladikshk.library.data.Author;

import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Integer> {

    @Query("select distinct a from Author a left join fetch a.books where a.id=:id")
    Optional<Author> findByIdWithBooks(@Param("id") int id);

}
