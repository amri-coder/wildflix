package com.wildflix.wildflix.servicesImplem;

import com.wildflix.wildflix.models.Section;
import com.wildflix.wildflix.repository.SectionRepository;
import com.wildflix.wildflix.services.SectionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SectionImplem implements SectionService {

    @Autowired
    SectionRepository sectionRepository;

    @Override
    public Section createSection(Section section) {
        return sectionRepository.save(section);
    }

    @Override
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    @Override
    public Section getSectionById(Long id) {
        Optional<Section> section = sectionRepository.findById(id);
        if(section.isPresent()) {
            return section.get();
        }else {
            return null;
        }
    }

    @Override
    public void deleteSectionById(Long id) {
    sectionRepository.deleteById(id);
    }

    @Override
    public Section modifySectionById(Long id, Section newSection) {
        Optional<Section> section = sectionRepository.findById(id);
        if(section.isPresent()) {
            section.get().setTitle(newSection.getTitle()) ;
            section.get().setDescription(newSection.getDescription());
            section.get().setVideos(newSection.getVideos());
            return section.get();
        }else {
            return null;
        }
    }
}
