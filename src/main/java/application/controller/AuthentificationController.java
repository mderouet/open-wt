package application.controller;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import javax.security.sasl.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import application.model.Contact;
import application.repository.ContactDao;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
@RequestMapping(path = "/secure")
public class AuthentificationController {
	@Autowired
	private ContactDao contactRepository;

	// Jwt token retrieve
	@RequestMapping(value = "/login", method = RequestMethod.POST)

	public ResponseEntity<Map<String, String>> login(@RequestBody Contact contactLogin) throws AuthenticationException {

		String jwtToken = "";

		if (contactLogin.getEmail() == null || contactLogin.getPassword() == null) {
			throw new AuthenticationException("Please fill in username and password");
		}

		String email = contactLogin.getEmail();
		String password = contactLogin.getPassword();

		Contact contact = contactRepository.findByEmail(email);

		if (contact == null) {
			throw new AuthenticationException("User email not found.");
		}

		String pwd = contact.getPassword();

		if (!password.equals(pwd)) {
			throw new AuthenticationException("Invalid login. Please check your name and password.");
		}

		// We using "User", we should create an enumeration here, ADMIN / USER / etc..
		// Also we don't need to store id, since our email is Unique, but it will be
		// easier to handle
		jwtToken = Jwts.builder().setSubject(email).claim("roles", "user").claim("id", contact.getId())
				.setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretkey").compact();

		return new ResponseEntity<>(
				Collections.singletonMap("token", "Bearer" +" "+ jwtToken),
				HttpStatus.OK);
	}
}
