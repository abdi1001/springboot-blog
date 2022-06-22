package com.abdiahmed.springbootblog.error;

import com.abdiahmed.springbootblog.payload.ErrorDetails;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorDetails> resourceNotFoundException(
      ResourceNotFoundException exception, WebRequest request) {
    ErrorDetails errorDetails =
        new ErrorDetails(exception.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UpdateResourceException.class)
  public ResponseEntity<ErrorDetails> updateResourceException(
      UpdateResourceException exception, WebRequest request) {
    ErrorDetails errorDetails =
        new ErrorDetails(exception.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BlogAPIException.class)
  public ResponseEntity<ErrorDetails> blogAPIExceptionException(
      BlogAPIException exception, WebRequest request) {
    ErrorDetails errorDetails =
        new ErrorDetails(exception.getMessage(), request.getDescription(false));
    System.out.println("From sout " + exception.getMessage());
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ResourceExist.class)
  public ResponseEntity<ErrorDetails> resourceExist(ResourceExist exception, WebRequest request) {
    ErrorDetails errorDetails =
        new ErrorDetails(exception.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorDetails> runtimeException(
      RuntimeException exception, WebRequest request) {
    ErrorDetails errorDetails =
        new ErrorDetails(exception.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDetails> exception(Exception exception, WebRequest request) {
    ErrorDetails errorDetails =
        new ErrorDetails(exception.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(SignatureException.class)
  public ResponseEntity<ErrorDetails> signatureException(
      SignatureException exception, WebRequest request) {
    ErrorDetails errorDetails =
        new ErrorDetails(exception.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InsufficientAuthenticationException.class)
  public ResponseEntity<ErrorDetails> insufficientAuthenticationException(
      InsufficientAuthenticationException exception, WebRequest request) {
    ErrorDetails errorDetails =
        new ErrorDetails(exception.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
}
