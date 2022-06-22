package com.abdiahmed.springbootblog.error;

public class ResourceExist extends RuntimeException {
  public ResourceExist(String message) {
    super(message);
  }
}
