package domain.frecuenciasDeEventos;
import java.time.*;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import domain.enums.TipoFrecuencia;
@Entity
public class FrecuenciaDiaria extends FrecuenciaDeEvento{
//	@Id @GeneratedValue
//	long id;
	private int limiteDeProximidad = 8;
	int hora;
	public FrecuenciaDiaria() {}
	public FrecuenciaDiaria(int hora) {
		this.hora=hora;
	}
	
	public TipoFrecuencia getFrecuencia() {return TipoFrecuencia.Diario;}
	
	public boolean esProximo(LocalDateTime fechaActual) {
		return this.diferenciaEnHorasEntreDosHoras(hora,fechaActual.getHour())<=limiteDeProximidad;
	}

	public int diferenciaEnHorasEntreDosHoras(int horaFinal, int horaComienzo) {
		int horas= horaFinal-horaComienzo;
		if (horaFinal<horaComienzo){ 
			horas+=24;
		};
		return horas;
	}
	public LocalDateTime cualEsLaFechaProxima(LocalDateTime fechaComienzo){
		int horasEntreComienzoEvento =this.diferenciaEnHorasEntreDosHoras(hora,fechaComienzo.getHour());
		return fechaComienzo.plusHours(horasEntreComienzoEvento);
	}
}