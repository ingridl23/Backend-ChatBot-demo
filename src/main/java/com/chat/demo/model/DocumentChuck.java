package com.chat.demo.model;

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

@Entity
@Table(name = "document_chucks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentChuck {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	  @ManyToOne
	    @JoinColumn(name = "document_id")
	    private Document document;
	  
	    private String content;
	  
	    private Integer chunckIndex;
	    
	    //private Long embedding_id; depende como se genere pero lo mas probable que se genere codigo number + string
	    private String embeddingId;
	    
	
}
