package se.fehm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "workItems")
public final class WorkItem extends AbstractEntity {

	@XmlElement
	@Column(nullable = false, unique = true)
	private String number;
	@XmlElement
	@Column(nullable = false)
	private String name;
	@XmlElement
	@Column(nullable = false)
	private String status;
	@XmlElement
	private boolean active;

	@ManyToOne
	@JoinColumn
	private Issue issue;

	protected WorkItem() {}

	public WorkItem(String number, String name, String status, boolean active) {
		this.number = number;
		this.name = name;
		this.status = status;
		this.active = active;
	}

	public String getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	public boolean isActive() {
		return active;
	}

	@Override
	public String toString() {
		return number + ", " + name + ", " + status + ", " + active;
	}

}
