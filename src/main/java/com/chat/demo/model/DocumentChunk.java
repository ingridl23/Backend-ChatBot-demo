package com.chat.demo.model;

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

@Entity
@Table(name = "document_chunks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentChunk {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	  @ManyToOne
	    @JoinColumn(name = "document_id")
	    private DocumentStored document;
	  
	  @Column(columnDefinition = "TEXT")
	    private String content;
	  
	    private Integer chunkIndex;
	    
	    //private Long embedding_id; depende como se genere pero lo mas probable que se genere codigo number + string
	    private String embeddingId;
	    
	
}
