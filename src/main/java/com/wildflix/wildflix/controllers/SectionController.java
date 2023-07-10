package com.wildflix.wildflix.controllers;

import com.wildflix.wildflix.models.Category;
import com.wildflix.wildflix.models.Section;
import com.wildflix.wildflix.services.SectionService;
import com.wildflix.wildflix.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SectionController {

    @Autowired
    SectionService sectionService;
    @Autowired
    VideoService videoService;

    /**
     * Create Section
     * @param section
     * @return
     */
    @PostMapping("/admin/sections")
    public ResponseEntity<Section> createSection(@RequestBody Section section) {
        return
                new ResponseEntity<>(sectionService.createSection(section),
                        HttpStatus.CREATED);
    }
    /**
     * Show All sections
     * @return
     */
    @GetMapping("/sections")
    public ResponseEntity<List<Section>> getAllSections(){
        return
                new ResponseEntity<>(sectionService.getAllSections(),
                        HttpStatus.OK);
    }
    /**
     * Show Section By ID
     * @param id
     * @return
     */
    @GetMapping("/sections/{id}")
    public ResponseEntity<Section> getSectionById(@PathVariable Long id){
        Section result = sectionService.getSectionById(id);
        if(result != null) {
            return new ResponseEntity<>(result,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Delete Section By ID
     * @param id
     * @return
     */
    @DeleteMapping("/admin/sections/{id}")
    public ResponseEntity<?> deleteSectionById(@PathVariable Long id){
        Section section = sectionService.getSectionById(id);
        if(section !=null) {
            sectionService.deleteSectionById(id);
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Modification the section by id
     * @param id
     * @return
     */
    @PutMapping("/admin/sections/{id}")
    public ResponseEntity<?> modifySectionById(@PathVariable Long id, @RequestBody Section newSection){
        Section section = sectionService.getSectionById(id);
        if(section !=null) {
            sectionService.modifySectionById(id, newSection);
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{sectionId}/videos/{videoId}")
    public ResponseEntity<String> removeVideoFromSection(
            @PathVariable("sectionId") Long sectionId,
            @PathVariable("videoId") Long videoId) {
        sectionService.deleteVideoFromSection(sectionId, videoId);
        return ResponseEntity.ok("Video removed from section successfully");
    }
}
