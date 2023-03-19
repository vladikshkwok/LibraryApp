package ru.vladikshk.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vladikshk.library.data.Author;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Integer> {
}
