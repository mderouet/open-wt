package application.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Cannot find Skill for this id parameter")
public class SkillNotFoundException extends Exception {

	private static final long serialVersionUID = -9033673074565234371L;

	public SkillNotFoundException(String message) {
		super(message);
	}
	
	public SkillNotFoundException(String message, EmptyResultDataAccessException emptyResultDataAccessException) {
		super(message);
	}

}
