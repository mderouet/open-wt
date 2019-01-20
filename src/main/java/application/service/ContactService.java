package application.service;

import application.model.Contact;

public interface ContactService {
	
	Contact findByEmail(String email);
	
}
