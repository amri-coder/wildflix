package com.wildflix.wildflix.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Section {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    private String title;
    private String description;

    @ManyToMany
    @JoinTable(name="sections_videos",
    joinColumns = @JoinColumn(name="section_id"),
            inverseJoinColumns = @JoinColumn(name="video_id")
    )

    private List<Video> videos = new ArrayList<>();

}
