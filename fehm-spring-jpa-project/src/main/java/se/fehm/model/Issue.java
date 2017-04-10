package se.fehm.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "issues")
public final class Issue extends AbstractEntity {
	
	@XmlElement
	@Column(nullable = false, unique = true)
	private String number;
	@XmlElement
	@Column(nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "issue")
	private Collection<WorkItem> workItems;
	
	protected Issue() {}
	
	public Issue(String number, String name) {
		this.number = number;
		this.name = name;
	}
	
	public String getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return number + ", " + name;
	}

}
