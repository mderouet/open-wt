package application.model;

import java.util.HashSet;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import application.structure.LevelEnum;
import application.structure.SkillNameEnum;

// Constraint key compound on both name/level (we don't want any duplicate here)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name", "level" }))
@Entity
public class Skill {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// O;N-O;N relationship between Skill & Contact
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "skills")
	@JsonBackReference
	private Set<Contact> contacts = new HashSet<>();

	/*
	 * We don't want to let the end user creating dumb values, we need a bit more
	 * structure, (we could have done this by creating a table and store references
	 * to this mysql table) Our design has several advantages and disadvantages =>
	 * We need more skills, we don't need a database administrator to push data for
	 * us as far as we can handle the enumeration here. But this is a problem if 1)
	 * We want to add categories by another way, another platform 2) We don't want
	 * to depend on this API to add Data. Example : i can insert values which are
	 * not in my enumeration by accessing directly to the mysql database
	 */

	@Column(length = 150)
	@Enumerated(EnumType.STRING)
	private SkillNameEnum name;

	@Column(length = 150)
	@Enumerated(EnumType.STRING)
	private LevelEnum level;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Contact> getContacts() {
		return this.contacts;
	}

	public void setUsers(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public SkillNameEnum getName() {
		return name;
	}

	public void setName(SkillNameEnum name) {
		this.name = name;
	}

	public LevelEnum getLevel() {
		return level;
	}

	public void setLevel(LevelEnum level) {
		this.level = level;
	}

}
