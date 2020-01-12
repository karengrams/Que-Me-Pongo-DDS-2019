package domain;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import org.uqbar.commons.model.annotations.Observable;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import domain.apisClima.MockAPI;
import domain.apisClima.ProveedorClima;
import domain.enums.TipoUsuario;
import domain.frecuenciasDeEventos.FrecuenciaAnual;
import domain.frecuenciasDeEventos.FrecuenciaDiaria;
import domain.frecuenciasDeEventos.FrecuenciaMensual;
import domain.frecuenciasDeEventos.FrecuenciaSemanal;
import domain.frecuenciasDeEventos.FrecuenciaUnicaVez;
import ui.EventoView;

@Observable
public class QueMePongoModel implements WithGlobalEntityManager {
	private int fechaInicio;
	private int fechaFin;
	private Set<Evento> eventos = new HashSet<Evento>();
	private String messageError;
	//InicializamosElRepocitorioConAlgunosEventos
	ProveedorClima APIDeMentiritas = new MockAPI(21,23,false);
	Evento eventoConFrecuenciaUnica = new Evento(new FrecuenciaUnicaVez(2019,5,24),"Sin descripcion");//"24-05-2019"
	Evento eventoConFrecuenciaDiaria = new Evento(new FrecuenciaDiaria(8),"Sin descripcion");//"16-01-2019"
	Evento eventoConFrecuenciaSemanal = new Evento(new FrecuenciaSemanal(3),"Sin descripcion");//"16-01-2019" MIERCOLES
	Evento eventoConFrecuenciaMensual = new Evento(new FrecuenciaMensual(16),"Sin descripcion");//"16-01-2019"
	Evento eventoConFrecuenciaAnual = new Evento(new FrecuenciaAnual(2,16),"Sin descripcion");//"16-01-2019"
	Usuario juan = new Usuario(TipoUsuario.PREMIUM,15,"juan","123");
	public QueMePongoModel() {
		Sugeridor.getInstance().setProveedorDeClima(APIDeMentiritas);
		juan.agendarEvento(eventoConFrecuenciaAnual);
		juan.agendarEvento(eventoConFrecuenciaDiaria);
		juan.agendarEvento(eventoConFrecuenciaMensual);
		juan.agendarEvento(eventoConFrecuenciaSemanal);
		juan.agendarEvento(eventoConFrecuenciaUnica);
	}
//////////////////////////////////////GETS_Y_SETS/////////////////////////////////////////////////	
	public int getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(int fechaInicio) {
			this.fechaInicio=fechaInicio;
			
	}

	public int getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(int fechaFinal) {
			this.fechaFin = fechaFinal;
	}
	
	
	public String getMessageError() {
		return messageError;
	}

	public Set<Evento> getEventos() { return eventos; }

/////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static LocalDateTime fecha(int fechaEnNro) {
		return LocalDateTime.of(LocalDate.of(fechaEnNro/10000,(fechaEnNro%10000)/100,(fechaEnNro%10000)%100),LocalTime.of(0,0,0));
	}
	
	public void validar() {
		this.messageError = "";
		if(Integer.toString(fechaFin).length()!=8 || Integer.toString(fechaInicio).length()!=8) {
			this.messageError = "Error: La fecha ingresada no es valida";
		}
		if(fechaFin<fechaInicio)
			this.messageError = "Error: La fecha comienzo debe ser anterior a la fecha fin";
	}
	
	
	public void listarEventos() {
		this.validar();
		EventoView.getInstance().setQueMePongoModel(this);
		eventos =RepositorioDeUsuarios.getInstance()
				.eventos()
				.stream()
				.filter(evento->evento.sucedeEntreEstasfechas(fecha(fechaInicio),fecha(fechaFin)))
				.collect(Collectors.toSet());		
	}
	
}
