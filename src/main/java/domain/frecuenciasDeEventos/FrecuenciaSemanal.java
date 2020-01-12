package domain.frecuenciasDeEventos;
import java.time.*;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import domain.enums.TipoFrecuencia;
@Entity
public class FrecuenciaSemanal extends FrecuenciaDeEvento{
	private int limiteDeProximidad = 2;
	private int diaDeLaSemana;
	public FrecuenciaSemanal() {}
	public FrecuenciaSemanal(int diaDeLaSemana) {
		this.diaDeLaSemana= diaDeLaSemana;
	}
	public TipoFrecuencia getFrecuencia() {return TipoFrecuencia.Semanal;}

	
	public boolean esProximo(LocalDateTime fechaActual) {
		return this.diferenciaEntreDosDias(diaDeLaSemana,fechaActual.getDayOfWeek().getValue())<=limiteDeProximidad;
	}
	public long diferenciaEntreDosDias(int fechaFin,int fechaComienzo) {
		int dias= fechaFin-fechaComienzo;
		if (fechaFin<fechaComienzo){ 
			dias+=7;
		};
		return dias;
	}
	public LocalDateTime cualEsLaFechaProxima(LocalDateTime fechaComienzo){
		long diasEntreComienzoEvento =this.diferenciaEntreDosDias(diaDeLaSemana,fechaComienzo.getDayOfWeek().getValue());
		return fechaComienzo.plusDays(diasEntreComienzoEvento);
	}
}
