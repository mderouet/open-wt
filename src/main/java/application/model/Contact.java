package application.model;

import java.util.HashSet;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// Email is used to authenticate our users, due to this design choice,it must be unique in DB
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
@Entity
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// 0;N relation : a skill is related to 0;N Contacts and a contact may have
	// multiple skill
	// Persisting a contact->addSkill should also persist skill->addContact
	// (question of references)
	// Merge => Database sync data ORM to Mysql in our example
	// Contact is the master of this relation here
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "contact_skill", joinColumns = { @JoinColumn(name = "contact_id") }, inverseJoinColumns = {
			@JoinColumn(name = "skill_id") })
	private Set<Skill> skills = new HashSet<>();

	@NotNull
	@Size(max = 30)
	private String firstname;

	@Size(max = 30)
	private String lastname;

	@Size(max = 100)
	private String address;

	@Email
	@Size(max = 50)
	private String email;

	// We are not using password validation but we could have restrict @Size and
	// looking for different character alpha/numeric/maj (regex)
	private String password;

	@Size(min = 10, max = 10)
	private String mobilePhoneNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	// No need to store here, this data can be processed
	public String getFullname() {
		return !(this.firstname.isEmpty() && this.lastname.isEmpty()) ? (firstname + " " + this.lastname) : ("");
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

}
