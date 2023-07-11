package com.wildflix.wildflix.servicesImplem;

import com.wildflix.wildflix.models.Category;
import com.wildflix.wildflix.models.Section;
import com.wildflix.wildflix.models.Video;
import com.wildflix.wildflix.repository.SectionRepository;
import com.wildflix.wildflix.repository.VideoRepository;
import com.wildflix.wildflix.services.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SectionImplem implements SectionService {

    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    VideoRepository videoRepository;

    @Override
    public Section createSection(Section section) {
        List<Video> videos = new ArrayList<>();
        section.getVideos().forEach(
                video -> videos.add(videoRepository.findById(video.getId()).orElse(null))
        );
        section.setVideos(videos);
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
    public void deleteVideoFromSection(Long sectionId, Long videoId){
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new IllegalArgumentException("Section not found"));

        Video videoToRemove = section.getVideos().stream()
                .filter(video -> video.getId().equals(videoId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Video not found in section"));

        section.getVideos().remove(videoToRemove);

        sectionRepository.save(section);
    }

    @Override
    public Section modifySectionById(Long id, Section newSection) {
        Optional<Section> section = sectionRepository.findById(id);
        if(section.isPresent()) {
            Section s = section.get();
            s.setTitle(newSection.getTitle()) ;
            s.setDescription(newSection.getDescription());
            s.setVideos(newSection.getVideos());
            return sectionRepository.save(s);

        }else {
            return null;
        }
    }

    @Override
    public List<Video> getSectionMovies(Long id){
        Optional<Section> section = sectionRepository.findById(id);
        if(section!=null){
            return section.get().getVideos();
        }
        return null;
    }
}
