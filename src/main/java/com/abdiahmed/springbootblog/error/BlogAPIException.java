package com.abdiahmed.springbootblog.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogAPIException extends RuntimeException {
  private String message;
  private HttpStatus status;

  public BlogAPIException(String message) {
    super(message);
    this.message = message;
  }

  public BlogAPIException(HttpStatus badRequest, String message) {
    super(message);
    this.message = message;
    this.status = badRequest;
  }

  public HttpStatus getStatus() {
    return status;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
