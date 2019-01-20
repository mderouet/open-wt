package application.controller;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import application.exception.ContactNotFoundException;
import application.model.Contact;
import application.model.Skill;
import application.repository.ContactDao;

@Controller
@RequestMapping(path = "/api/contact")
public class ContactController {
	@Autowired
	private ContactDao contactRepository;

	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<Contact> modifyContact(@RequestBody @Valid Contact newContact,
			@PathVariable Integer id) throws ContactNotFoundException {

		Optional<Contact> contactOpt = contactRepository.findById(id);

		if (contactOpt.isPresent()) {
			Contact contact = contactOpt.get();
			contact.setFirstname(newContact.getFirstname());
			contact.setLastname(newContact.getLastname());
			contact.setAddress(newContact.getAddress());
			contact.setEmail(newContact.getEmail());
			contact.setMobilePhoneNumber(newContact.getMobilePhoneNumber());
			return new ResponseEntity<>(contactRepository.save(contact), HttpStatus.OK);
		} else {
			// We could have save our contact here like "modify if exist, create if not"
			throw new ContactNotFoundException("Cannot find Contact for id : " + id);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteContact(@PathVariable Integer id) throws ContactNotFoundException {
		try {
			contactRepository.deleteById(id);
		}
		catch (EmptyResultDataAccessException emptyResultDataAccessException) {
			throw new ContactNotFoundException(emptyResultDataAccessException.getMessage(),emptyResultDataAccessException);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/")
	public @ResponseBody ResponseEntity<Iterable<Contact>> getAllContact() {
		return new ResponseEntity<>(contactRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<Optional<Contact>> contactById(@PathVariable Integer id)
			throws ContactNotFoundException {
		Optional<Contact> contact = contactRepository.findById(id);
		if (contact.isPresent()) {
			return new ResponseEntity<>(contact, HttpStatus.OK);
		} else {
			throw new ContactNotFoundException("Cannot find Contact for id : " + id);
		}
	}

	// return only Skills by contact-id
	@GetMapping("/{id}/skills")
	public @ResponseBody ResponseEntity<Iterable<Skill>> skillsByContactId(@PathVariable Integer id)
			throws ContactNotFoundException {

		Optional<Contact> contact = contactRepository.findById(id);

		if (contact.isPresent()) {
			return new ResponseEntity<>(contact.get().getSkills(), HttpStatus.OK);
		} else {
			throw new ContactNotFoundException("Cannot find Contact for id : " + id);
		}
	}
	
}
