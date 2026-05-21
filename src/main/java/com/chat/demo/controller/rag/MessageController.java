package com.chat.demo.controller.rag;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.demo.model.Message;
import com.chat.demo.service.rag.MessageService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@Getter
@Setter
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
	
	private final MessageService mensa;
	
	// crear o guardar un mensaje
	
	  @PostMapping
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	  public Message saveMessage(@RequestBody Message men) {
		  return mensa.save(men);
	  }
	  
	  
   // modificar un mensaje
	  
	  @PutMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	  public Message updateMessage(@PathVariable long id , @RequestBody Message men) {
		  return mensa.update(id, men);
	  }

	  // encuentra un mensaje por id
	  
	  @GetMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	  public Optional<Message> findMessageById(@PathVariable Long id){
		return mensa.findById(id);
	  }
	  
	  // encuentra un mensaje por su titulo
	  
	  @GetMapping("/title/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	  public Optional<Message> findMessageByTitle(@PathVariable String title){
		return mensa.findByTitle(title);
	  }
	  
	  // encuentra un mensaje por conversacion
	  
	  @GetMapping("/conversation/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	  public List<Message> findMessageByConversationById(@PathVariable Long id){
		return mensa.findByConversation(id);
	  }
	  
	  
	  // encuentra una conversacion por usuario
	  @GetMapping("/user/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN','USER')")
	  public List<Message> findMessageByUser(@PathVariable Long id){
		return mensa.findByUser(id);
	  }
	  
	  // elimina un mensaje
	  @DeleteMapping("/{id}")
	  @PreAuthorize("hasAnyRole('ADMIN')")
	  public void deleteMessage(@PathVariable Long id){
		mensa.delete(id);
	  }
	  
	  
}
