package com.chat.demo.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentStored {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String filePath;

    @ManyToOne
    @JoinColumn(name = "document_status_id")
    private  DocumentStatus status;

    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "documents_areas",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    private Set<Area> areas = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;
    
    private String fileName;
    private String mimeType;
    private Long fileSize;
    
    private LocalDateTime createdAt;
}