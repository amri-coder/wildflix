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
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String firstname;
	private String lastname;
	
	//l'email doit être unique
	@Column(unique=true, nullable=false)
	private String email;
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "favorite",
			joinColumns = {
					@JoinColumn(name = "user_id")
			},inverseJoinColumns = {
			@JoinColumn(name = "video_id")
	})
	private List<Video> favorite = new ArrayList<>();


}
