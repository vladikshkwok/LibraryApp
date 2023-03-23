package ru.vladikshk.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vladikshk.library.data.Book;
import ru.vladikshk.library.data.Tag;

import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Integer> {

    @Query("select distinct t from Tag t left join fetch t.books where t.id=:id")
    Optional<Tag> findByIdWithBooks(@Param("id") int id);

}
