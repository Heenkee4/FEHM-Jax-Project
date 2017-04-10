package se.fehm.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@MappedSuperclass
public abstract class AbstractEntity {

	@Id
	@GeneratedValue
	private Long id; 

	public Long getId() {
		return id;
		
	}
	
}
