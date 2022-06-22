package com.abdiahmed.springbootblog.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErrorDetails {
  private String message;
  private String details;
  private Date time;

  public ErrorDetails(String message, String details) {
    this.message = message;
    this.details = details;
    this.time = new Date();
  }
}
