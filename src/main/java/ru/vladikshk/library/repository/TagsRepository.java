package ru.vladikshk.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vladikshk.library.data.Tag;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Integer> {
}
