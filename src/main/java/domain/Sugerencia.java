package domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import domain.enums.TipoSugerencias;

@Entity 
public class Sugerencia extends SuperClase{

	@ManyToMany
	private Set<Prenda> atuendo;

	@Enumerated(EnumType.STRING)
	private TipoSugerencias estado = TipoSugerencias.PENDIENTE;

	@ManyToOne
	private Evento evento;
	
	

	public Sugerencia() {}
	public Sugerencia(Set<Prenda> unAtuendo, Evento evento) {
		this.atuendo = unAtuendo;
		this.evento = evento;
	}

	public void setPrendasComoNoUsadas() {
		atuendo.forEach(prenda -> prenda.setUsada(false));
	}

	public TipoSugerencias getEstado() {
		return estado;
	}

	public void setEstado(TipoSugerencias estado) {
		this.estado = estado;
	}

	public Set<Prenda> getAtuendo() {
		return atuendo;
	}

	public Evento getEvento() {
		return evento;
	}

	public boolean mismoEvento(Evento evento) {
		return this.evento == evento;
	}

	public boolean aceptada() {
		return this.estado == TipoSugerencias.ACEPTADA;
	}

}
