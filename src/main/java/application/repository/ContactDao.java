package application.repository;

import org.springframework.data.repository.CrudRepository;
import application.model.Contact;

public interface ContactDao extends CrudRepository<Contact, Integer> {
	
	Contact findByEmail(String email);

}
