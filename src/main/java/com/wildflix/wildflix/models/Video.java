package com.wildflix.wildflix.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	
	private String title;
	private String description;
	
	
	private boolean isPrivate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date releaseDate;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "categories",
			joinColumns = {
					@JoinColumn(name = "video_id")
			},inverseJoinColumns = {
			@JoinColumn(name = "category_id")
	})
	private List<Category> categories = new ArrayList<>();
	
	//l'url de la video "l'endroit où vous allez stocker la video"
	
	

}
