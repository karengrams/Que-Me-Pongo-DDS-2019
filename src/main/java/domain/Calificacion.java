package domain;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

import domain.enums.Categoria;
import domain.enums.TipoSensaciones;
@Entity
public class Calificacion extends SuperClase{
	@Enumerated
	private Categoria parteCuerpo;
	@Enumerated
	private TipoSensaciones sensacion;
	public Calificacion() {}
	public Calificacion(Categoria parteCuerpo, TipoSensaciones sensacion) {
		this.parteCuerpo = parteCuerpo;
		this.sensacion = sensacion;
	}

	public Categoria getCategoria() {
		return this.parteCuerpo;
	}

	public TipoSensaciones getSensacion() {
		return this.sensacion;
	}
}
