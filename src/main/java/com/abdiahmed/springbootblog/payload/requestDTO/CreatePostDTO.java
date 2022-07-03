package com.abdiahmed.springbootblog.payload.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDTO {

  @NotEmpty
  @Size(min = 2, message = "title should have at lest 2 characters long")
  private String title;

  @NotEmpty
  @Size(min = 2, message = "body should have at lest 2 characters long")
  private String body;
}
