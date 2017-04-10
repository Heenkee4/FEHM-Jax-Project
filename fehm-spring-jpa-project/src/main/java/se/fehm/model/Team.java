package se.fehm.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "teams")
public final class Team extends AbstractEntity {

	@XmlElement
	@Column(nullable = false, unique =true)
	private String number;
	@XmlElement
	@Column(nullable = false)
	private String name;
	@XmlElement
	private boolean active;
	
	@OneToMany(mappedBy = "team")
	private Collection<User> user;
	
	protected Team() {}
	
	public Team(String number, String name, boolean active) {
		this.number = number;
		this.name = name;
		this.active = active;
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isActive() {
		return active;
	}
	
	@Override
	public String toString() {
		return number + ", " + name + ", " + active;
	}
	
}

