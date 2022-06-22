package com.abdiahmed.springbootblog.error;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
  private final String field;
  private final String resource;
  private long fieldValue;
  private String name;

  public ResourceNotFoundException(String resource, String field, long fieldValue) {
    super(String.format("%s not found with %s : %s", resource, field, fieldValue));
    this.field = field;
    this.resource = resource;
    this.fieldValue = fieldValue;
  }

  public ResourceNotFoundException(String resource, String field, String name) {
    super(String.format("%s not found with %s : %s", resource, field, name));
    this.field = field;
    this.resource = resource;
    this.name = name;
  }
}
