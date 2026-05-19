package com.chat.demo.model;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organization {
	

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(unique = true, nullable = false)
	    private String name;
	    
	    private String logoUrl;
	    
	    private String primaryColor;
	    
	    private String secondaryColor;
	    
	    private String faviconUrl;
	    
	    @Column(unique = true)
	    private String domain;
	    
	    @Column(nullable = false)
	    private String supportEmail;
	    
	    private LocalDateTime createdAt;

	    @OneToMany(mappedBy = "organization")
	    private List<User> users;

	    @OneToMany(mappedBy = "organization")
	    private List<Area> areas;

	    @OneToMany(mappedBy = "organization")
	    private List<Document> documents;
	    
	    @OneToMany(mappedBy = "organization")
	    private List<Integration> integrations;
	    
	}

