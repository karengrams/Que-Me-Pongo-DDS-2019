package domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SuperClase {
	@Id @GeneratedValue
	private Long  id;

	public Long getId() {
		return id;
	}
	
}
