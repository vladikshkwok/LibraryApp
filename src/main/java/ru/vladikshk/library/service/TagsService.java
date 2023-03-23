package ru.vladikshk.library.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vladikshk.library.data.Tag;
import ru.vladikshk.library.dto.TagDTO;
import ru.vladikshk.library.dto.TagDetailsDTO;
import ru.vladikshk.library.mapper.TagMapper;
import ru.vladikshk.library.repository.TagsRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TagsService {

    private final TagsRepository tagsRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagsService(TagsRepository tagsRepository, TagMapper tagMapper) {
        this.tagsRepository = tagsRepository;
        this.tagMapper = tagMapper;
    }

    public List<TagDTO> findAll() {
        return tagsRepository.findAll().stream()
                .map(tagMapper::tagToTagDTO)
                .collect(Collectors.toList());
    }

    public TagDetailsDTO findOne(int id) {
        return tagsRepository.findByIdWithBooks(id).map(tagMapper::tagToTagDetailsDTO).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public TagDetailsDTO save(TagDTO tag) {
        return tagMapper.tagToTagDetailsDTO(tagsRepository.save(tagMapper.tagDTOToTag(tag)));
    }

    @Transactional
    public TagDetailsDTO update(int id, TagDetailsDTO updatedTag) {
        Tag tag = tagMapper.tagDetailsDTOToTag(updatedTag);
        tag.setId(id);
        return tagMapper.tagToTagDetailsDTO(tagsRepository.save(tag));
    }

    @Transactional
    public void delete(int id) {
        tagsRepository.deleteById(id);
    }
}
