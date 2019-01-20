package application.exception;

import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	// Handle future custom exceptions, for example, we have
	// ContactNotFoundException & SkillNotFoundException & SkillFormatException here

	@ExceptionHandler({ ContactNotFoundException.class, SkillNotFoundException.class})
	public final ResponseEntity<ErrorOutput> handleUserExceptions(Exception ex, WebRequest request) {
		ErrorOutput errorMessage = new ErrorOutput(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

}