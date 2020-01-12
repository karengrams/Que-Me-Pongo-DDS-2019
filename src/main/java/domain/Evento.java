package domain;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import domain.enums.*;
import org.uqbar.commons.model.annotations.Observable;
import domain.frecuenciasDeEventos.*;
import ui.EventoView;

@Entity
public class Evento extends SuperClase{

	private String descripcion;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private FrecuenciaDeEvento frecuencia;

	private Evento() {}/////////////////// Solo para la Persistencia
	// private int contador=0;// usado solo de forma provisoria para el
	// JobsEventosTest

	public Evento(FrecuenciaDeEvento unaFrecuencia, String unaDescripcion) {
		this.frecuencia = unaFrecuencia;
		this.descripcion = unaDescripcion;
	}

	public Set<Set<Prenda>> obtenerAtuendos(Usuario usuario) {
		return Sugeridor.getInstance().sugerirPrendasPara(usuario);
	}

	public void sugerir(Usuario usuario) {
		Sugeridor.getInstance().sugerirPrendasPara(usuario).forEach(atuendo -> usuario.agregarSugerencia(new Sugerencia(atuendo, this)));
		// this.setContador(contador+1);//Usado en forma provisoria para el
		// JobsEventosTest
	}

	public boolean yaSucedio() {
		return frecuencia.yaSucedio(LocalDateTime.now());
	}

	public boolean esProximo(LocalDateTime fechaActual) {
		return frecuencia.esProximo(fechaActual);
	}

	public boolean sucedeEntreEstasfechas(LocalDateTime fechaComienzo, LocalDateTime fechaFin) {
		return frecuencia.sucedeEntreEstasFechas(fechaComienzo, fechaFin);
	}

	////////////////////////////////////// GETS_Y_SETS/////////////////////////////////////////////////
	public TipoFrecuencia getFrecuencia() {
		return frecuencia.getFrecuencia();
	}
	public LocalDateTime getFecha() { ///Persistencia
		return this.cualEsLaFechaProxima(EventoView.getInstance().getFechaInicio());
	}
	public LocalDateTime cualEsLaFechaProxima(LocalDateTime fechaInicio) {
		return frecuencia.cualEsLaFechaProxima(fechaInicio);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public SugerenciasListas getSugerenciasListas() { // Es para Arena te tira si te tiro sugerencias, en caso de ser													// posibles.
		if (this.esProximo(LocalDateTime.now()) || this.yaSucedio())
			return SugerenciasListas.YES;
		else
			return SugerenciasListas.NO;
	}
	public String getFechaweb() { ///WEB
		LocalDateTime resp = this.cualEsLaFechaProxima(LocalDateTime.now());
		DateTimeFormatter format = DateTimeFormatter.ofPattern("'Fecha: ' yyyy'-'M'-'d 'Horario: ' h ':' mm");
		return resp.format(format);
	}
}
