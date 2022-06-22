package com.abdiahmed.springbootblog.error;

public class UpdateResourceException extends Exception {
  private long pathId;
  private long bodyId;

  public UpdateResourceException(long pathId, long bodyId) {
    super(String.format("Path Id: %s is not the same as body request Id: %s", pathId, bodyId));
  }
}
