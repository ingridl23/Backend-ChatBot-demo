package com.chat.demo.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * representa conexiones con sistemas externos:

RAFAM
portal ciudadano
RENAPER
APIs provinciales
sistemas legacy
webhooks
n8n
email/SMS providers

O sea: esto es literalmente el puente entre tu chatbot/intranet y el caos administrativo
 */


@Entity
@Table(name = "integrations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Integration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "type_id")
    private IntegrationType type;
    private String authType;
    private String baseUrl;
    
    @Column(columnDefinition = "TEXT")
    private String apiKey;
    
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
    
    private Boolean isActive;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
	
}
