package application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.Contact;
import application.repository.ContactDao;
import application.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactDao contactDao;

	public Contact findByEmail(String email) {
		return contactDao.findByEmail(email);
	}
	
}
