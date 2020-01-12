package domain.frecuenciasDeEventos;

import java.time.*;
import domain.enums.TipoFrecuencia;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FrecuenciaMensual extends FrecuenciaDeEvento{
	private int limiteDeProximidad = 5;
	int diaDelMes;
	public FrecuenciaMensual() {}
	public FrecuenciaMensual(int dia) {
		this.diaDelMes = dia;
	}
	public TipoFrecuencia getFrecuencia() {return TipoFrecuencia.Mensual;}
	public boolean esProximo(LocalDateTime fechaActual) {
		return this.diferenciaEntreDosDias(diaDelMes,fechaActual.getDayOfMonth())<=limiteDeProximidad;
	}
	public long diferenciaEntreDosDias(int diaFin,int diaComienzo) {
		int dias= diaFin-diaComienzo;
		if (diaFin<diaComienzo){ 
			dias+=30 ;
		};
		return dias;
	}
	
	public LocalDateTime cualEsLaFechaProxima(LocalDateTime fechaComienzo){
		long diasEntreComienzoEvento =this.diferenciaEntreDosDias(diaDelMes,fechaComienzo.getDayOfMonth());
		return fechaComienzo.plusDays(diasEntreComienzoEvento);
	}
}
