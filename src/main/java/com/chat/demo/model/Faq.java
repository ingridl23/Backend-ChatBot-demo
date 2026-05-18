package com.chat.demo.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "faqs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Faq {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String question;
	    
	    private String answer;
	    
	    @ManyToOne
	    @JoinColumn(name = "organization_id")
	    private Organization organization;
	    
	    @ManyToOne
	    @JoinColumn(name = "area_id")
	    private Area area;
	    
	    private Integer priority;  //escala del 1 al 5
	    
	    private Boolean isActive;

}
