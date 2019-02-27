package com.myuserapp.test.api.UserMaintApp.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.myuserapp.test.api.UserMaintApp.model.UserNotFoundError;
import com.myuserapp.test.api.UserMaintApp.model.UserValidationError;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ObjectNotFoundException.class)
  public final ResponseEntity<UserNotFoundError> handleUserNotFoundException(ObjectNotFoundException ex, WebRequest request) {
	  Map<String, String> details = new HashMap<String, String>();
	  details.put("message", "User "+ex.getMessage()+" not found");
	  UserNotFoundError errorAtrributes = new UserNotFoundError(details, "NotFoundError");
    return new ResponseEntity<>(errorAtrributes, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CustomException.class)
  public final ResponseEntity<UserValidationError> handleValidationException(CustomException ex, WebRequest request) {
    UserValidationError exceptionResponse = new UserValidationError("text","Must be between 1 and 50 chars long","ValidationError");
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

}
