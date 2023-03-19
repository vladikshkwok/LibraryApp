package ru.vladikshk.library.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vladikshk.library.data.Tag;
import ru.vladikshk.library.repository.TagsRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TagsService {

    private final TagsRepository tagsRepository;

    @Autowired
    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public List<Tag> findAll() {
        return tagsRepository.findAll();
    }

    public Tag findOne(int id) {
        Optional<Tag> tag = tagsRepository.findById(id);
        tag.ifPresent(a -> Hibernate.initialize(a.getBooks()));

        return tag.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void save(Tag tag) {
        tagsRepository.save(tag);
    }

    @Transactional
    public void update(int id, Tag tag) {
        tag.setId(id);
        tagsRepository.save(tag);
    }

    @Transactional
    public void delete(int id) {
        tagsRepository.deleteById(id);
    }
}
