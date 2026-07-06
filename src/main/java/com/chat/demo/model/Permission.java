package com.chat.demo.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	    // El lado dueño (Role.permissions) es el que se serializa; este lado inverso se
	    // ignora en JSON para no entrar en referencia circular Role -> Permission -> Role...
	    @ManyToMany(mappedBy = "permissions")
	    @JsonIgnore
	    private Set<Role> roles;
}
