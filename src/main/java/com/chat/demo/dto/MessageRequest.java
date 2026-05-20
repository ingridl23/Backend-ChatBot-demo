package com.chat.demo.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    private Long userId ;
    @NotBlank
    private String title;
    
   // private Boolean status;
    @NotBlank
    private String content;
    @NotBlank
    private String metadata;
 
    private Long senderTypeId;

    private Long conversationId;
    
}
