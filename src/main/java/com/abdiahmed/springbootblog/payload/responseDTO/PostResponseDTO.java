package com.abdiahmed.springbootblog.payload.responseDTO;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDTO {

  private Long id;
  private String username;
  private String title;
  private String body;
  private Set<CommentResponseDTO> comments;
}
