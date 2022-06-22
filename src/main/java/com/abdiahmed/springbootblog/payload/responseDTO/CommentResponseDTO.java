package com.abdiahmed.springbootblog.payload.responseDTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {
  private Long id;
  private String username;
  private String comment;
}
