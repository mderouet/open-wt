package application.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import application.exception.SkillNotFoundException;
import application.model.Skill;
import application.repository.SkillDao;

@Controller
@RequestMapping(path = "/api/skill")
public class SkillController {
	@Autowired
	private SkillDao skillRepository;

	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<Skill> modifySkill(@RequestBody @Valid Skill newSkill, @PathVariable Integer id)
			throws SkillNotFoundException {

		Optional<Skill> skillOpt = skillRepository.findById(id);

		if (skillOpt.isPresent()) {
			Skill skill = skillOpt.get();
			skill.setName(newSkill.getName());
			skill.setLevel(newSkill.getLevel());
			return new ResponseEntity<>(skillRepository.save(skill), HttpStatus.OK);
		} else {
			throw new SkillNotFoundException("Cannot find Skill for id : " + id);
		}
	}

	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<Void> deleteSkill(@PathVariable Integer id) throws SkillNotFoundException {
		try {
			skillRepository.deleteById(id);
		} catch (EmptyResultDataAccessException emptyResultDataAccessException) {
			throw new SkillNotFoundException(emptyResultDataAccessException.getMessage(),
					emptyResultDataAccessException);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/")
	public @ResponseBody ResponseEntity<Skill> addNewSkill(@RequestBody @Valid Skill newSkill) {
		return new ResponseEntity<>(skillRepository.save(newSkill), HttpStatus.CREATED);
	}

	@GetMapping(path = "/")
	public @ResponseBody ResponseEntity<Iterable<Skill>> getAllSkill() {
		return new ResponseEntity<>(skillRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<Optional<Skill>> skillById(@PathVariable int id) throws SkillNotFoundException {

		Optional<Skill> skill = skillRepository.findById(id);
		if (skill.isPresent()) {
			return new ResponseEntity<>(skill, HttpStatus.OK);
		} else {
			throw new SkillNotFoundException("Cannot find Skill for id : " + id);
		}
	}

}
