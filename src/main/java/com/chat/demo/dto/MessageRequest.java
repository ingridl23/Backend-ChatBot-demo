package com.chat.demo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
	@NotNull
    private Long userId ;
    @NotBlank
    private String title;
    
   // private Boolean status;
    @NotBlank
    private String content;
    private String metadata;
    @NotNull
    private Long senderTypeId;
    @NotNull
    private Long conversationId;
    @NotNull
    private Boolean status;
}
