package com.chat.demo.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(unique = true, nullable = false)
	    private String name;
	
	    @ManyToMany(mappedBy = "permissions")
	    private Set<Role> roles;
}
