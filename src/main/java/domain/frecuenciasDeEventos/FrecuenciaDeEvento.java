package domain.frecuenciasDeEventos;
import java.time.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import domain.enums.TipoFrecuencia;

@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Entity
public class FrecuenciaDeEvento{
	@Id
	@GeneratedValue
	private Long id;
	public FrecuenciaDeEvento(){}
	public TipoFrecuencia getFrecuencia() {
		return TipoFrecuencia.Anual;
	}
	public boolean esProximo(LocalDateTime fechaActual) {
		return false; //metodo sobrescrito en las subclases
	}

	public boolean sucedeEntreEstasFechas(LocalDateTime fechaComienzo, LocalDateTime fechaFin) {
		return this.cualEsLaFechaProxima(fechaComienzo).isBefore(fechaFin)
				|| this.cualEsLaFechaProxima(fechaComienzo).isEqual(fechaFin);
	}

	public LocalDateTime cualEsLaFechaProxima(LocalDateTime fechaComienzo) {
		return LocalDateTime.now();
	}

	public boolean yaSucedio(LocalDateTime fechaActual) {
		return !esProximo(fechaActual);
	}
}
