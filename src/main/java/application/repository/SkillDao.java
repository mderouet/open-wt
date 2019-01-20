package application.repository;


import org.springframework.data.repository.CrudRepository;

import application.model.Skill;

public interface SkillDao extends CrudRepository<Skill, Integer> {
}
