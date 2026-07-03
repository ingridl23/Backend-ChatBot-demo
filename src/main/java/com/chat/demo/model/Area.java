package com.chat.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "areas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "area")
    private List<User> users;

    @ManyToMany(mappedBy = "areas")
    private Set<DocumentStored> documents;

    @OneToMany(mappedBy = "area")
    private List<SystemLink> systems;
    
    
}
