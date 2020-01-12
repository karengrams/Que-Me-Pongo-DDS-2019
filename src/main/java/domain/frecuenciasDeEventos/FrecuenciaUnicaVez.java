package domain.frecuenciasDeEventos;

import java.time.*;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import domain.enums.TipoFrecuencia;
@Entity
public class FrecuenciaUnicaVez extends FrecuenciaDeEvento {
	static int limiteDeProximidad = 2;
	
	@Column
	private LocalDateTime fechaEvento;
	
	//------Constructores-------
	public FrecuenciaUnicaVez() {}
	public FrecuenciaUnicaVez(int anio, int mes, int dia) {
		fechaEvento = LocalDateTime.of(anio, mes, dia, 0, 0, 0);
	}
	public FrecuenciaUnicaVez(LocalDateTime fechaEvento) {
		this.fechaEvento = fechaEvento;
	}
	//--------------
	
	public TipoFrecuencia getFrecuencia() {
		return TipoFrecuencia.UNICO;
	}

	public boolean esProximo(LocalDateTime fechaActual) {
		Duration duracion = Duration.between(fechaActual, fechaEvento);
		return duracion.toDays() <= limiteDeProximidad && duracion.toDays() >= 0;
	}

	public LocalDateTime cualEsLaFechaProxima(LocalDateTime fechaComienzo) {
		return fechaEvento;
	}

	public boolean yaSucedio(LocalDateTime fechaActual) {
		return fechaEvento.isBefore(fechaActual);
	}
}
