package se.fehm.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "users")
public final class User extends AbstractEntity {

	@XmlElement
	@Column(unique = true, nullable = false)
	private String number;
	@XmlElement
	@Column(unique = true, nullable = false)
	private String userName;
	@XmlElement
	@Column(nullable = false)
	private String firstName;
	@XmlElement
	@Column(nullable = false)
	private String lastName;
	@XmlElement
	private boolean active;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn
	private Team team;

	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<WorkItem> workItem;

	protected User() {}

	public User(String number, String userName, String firstName, String lastName, boolean active) {
		this.number = number;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.active = active;
	}

	public String getNumber() {
		return number;
	}

	public String getUserName() {
		return userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public boolean isActive() {
		return active;
	}
	
	public String toString() {
		return number + ", " + userName + ", " + firstName + ", " + lastName + ", " + active;
	}

}
