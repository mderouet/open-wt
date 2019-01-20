package application.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Cannot find Contact for this id parameter")
public class ContactNotFoundException extends Exception {

	private static final long serialVersionUID = -952456154108858535L;

	public ContactNotFoundException(String message) {
		super(message);
	}
	
	public ContactNotFoundException(String message, EmptyResultDataAccessException emptyResultDataAccessException) {
		super(message);
	}
}
