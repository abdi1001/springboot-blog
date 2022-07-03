package com.abdiahmed.springbootblog.payload.requestDTO;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDTO {

  @NotEmpty
  @Size(min = 2, message = "comment should have at lest 2 characters long")
  private String comment;
}
