package com.wildflix.wildflix.services;

import com.wildflix.wildflix.models.Section;

import java.util.List;

public interface SectionService {

    Section createSection(Section section);
    List<Section> getAllSections();
    Section getSectionById(Long id);
    void deleteSectionById(Long id);
    Section modifySectionById(Long id, Section Section);
}
