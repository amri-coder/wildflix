package com.wildflix.wildflix.services;

import com.wildflix.wildflix.models.Section;
import com.wildflix.wildflix.models.Video;

import java.util.List;

public interface SectionService {

    Section createSection(Section section);
    List<Section> getAllSections();
    Section getSectionById(Long id);
    void deleteSectionById(Long id);
    void deleteVideoFromSection(Long sectionId, Long videoId);
    Section modifySectionById(Long id, Section Section);
    List<Video> getSectionMovies(Long id);
}
