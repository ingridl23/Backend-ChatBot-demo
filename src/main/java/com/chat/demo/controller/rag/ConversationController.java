package com.chat.demo.controller.rag;

import java.util.List;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chat.demo.dto.ConversationRequest;
import com.chat.demo.model.Conversation;
import com.chat.demo.service.rag.ConversationService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@Getter
@Setter
@RequestMapping("/api/conversation")
@RequiredArgsConstructor
public class ConversationController {

	private final ConversationService conversaServ;
	
	
	//crear , guardar o comenzar conversacion
	
	@PostMapping
	  @PreAuthorize("hasAnyRole('USER','ADMIN')")
	    public Conversation createConversation(@RequestBody ConversationRequest request) {
	        return conversaServ.createConversation(request.getUserId(), request.getTitle());
	    }
	
	
	
	//buscar una conversacion por su id
	
	@GetMapping("/{id}")
	  @PreAuthorize("hasAnyRole('USER')")
	    public Optional<Conversation> getConversationById(@PathVariable Long id) {
	        return conversaServ.findById(id);
	    }
	
	//buscar conversacion por usuario relacionado
	@GetMapping("/conversation/user/{id}")
	  @PreAuthorize("hasAnyRole('USER')")
	    public List<Conversation> getConversationByUserId(@PathVariable Long id) {
	        return conversaServ.findByUser(id);
	    }
	
	
	// buscar conversacion por su titulo
	
	@GetMapping("/title/{id}")
	  @PreAuthorize("hasAnyRole('USER')")
	    public Optional<Conversation> getConversationByTitleId(@PathVariable String title) {
	        return conversaServ.findByTitle(title);
	    }
	
	
	
	// buscar conversacion por usuario relacionado activo
	

	@GetMapping("/enabled/{id}")
	  @PreAuthorize("hasAnyRole('USER','ADMIN')")
	    public List<Conversation> getConversationByUserEnabledId(@PathVariable Long userId) {
	        return conversaServ.findActiveByUser(userId);
	    }
	
	/**
	// ver conversaciones
	
	  @GetMapping
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	    public List<Conversation> getAllConversatios() {
	        return conversaServ.getAllConversations();
	    }
	*/

	// modificar conversacion obteniendo su id 
	
	  @PatchMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
     public Conversation updateConversation(@PathVariable Long id, @RequestBody Conversation request) {
	   return conversaServ.update(id,request);
  }
	
	
	//eliminar conversacion
	 
	  @DeleteMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	  public void deleteConversation(@PathVariable Long id) {
		  conversaServ.delete(id);
	  }
	
	//cerrar o terminar conversacion
	 
	  @PatchMapping("close/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	  public void closeConversation(@PathVariable Long id) {
		  conversaServ.closeConversation(id);
	  }
	
}
