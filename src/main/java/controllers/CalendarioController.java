package controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import domain.Evento;
import domain.Prenda;
import domain.PrendaBuilder;
import domain.RepositorioDeUsuarios;
import domain.Sugerencia;
import domain.Usuario;
import domain.enums.Color;
import domain.enums.Material;
import domain.enums.TipoFrecuencia;
import domain.enums.TipoPrenda;
import domain.enums.TipoSugerencias;
import domain.frecuenciasDeEventos.FrecuenciaUnicaVez;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class CalendarioController implements WithGlobalEntityManager, TransactionalOps{
	public ModelAndView verSugerencia(Request req, Response res) {
		String numString=req.queryParams("eventoNum");
		int numEvento = Integer.parseInt(numString);
		List<Evento> eventosPendientes=req.session().attribute("EventosPendientes");
		req.session().removeAttribute("EventosPendientes");
		Evento evento = eventosPendientes.get(numEvento);
		res.redirect("/eventos/"+ evento.getId().toString()+"/sugerencias/pendientes");
		return null;
	}
	public String verCalendario(Request req, Response res) {
		String fecha=req.queryParams("fecha");
		Boolean hayFecha;
		Boolean noHayEventos=true;
		if (fecha!=null) {
			hayFecha = (fecha.length()!=0);
		}else {
			hayFecha = false;
		}
		Usuario usuarie = RepositorioDeUsuarios.getInstance().buscarPorNombre(req.cookie("nombreUsuario"));
		List<Evento>eventoList = null;;
		List<Evento>eventosNoPendientes = null;
		List<Evento> eventosPendientes = null;
		eventoList = hayFecha? calcularEventos(fecha,usuarie):null;
		if(eventoList!=null) {
			if(!eventoList.isEmpty()) noHayEventos=false;
			eventosPendientes= tieneSugerenciasPendientes(eventoList,usuarie);
			eventosNoPendientes= eventoList;
			if (eventosPendientes!=null)
				eventosNoPendientes.removeAll(eventosPendientes);
			req.session().attribute("EventosPendientes",eventosPendientes);
		}else {
			eventosPendientes = null;
			eventosNoPendientes=null;
		}
		
		HashMap<String, Object> viewModel = new HashMap<>();
		viewModel.put("eventosPendientes", eventosPendientes);
		viewModel.put("eventosNoPendientes", eventosNoPendientes);
		viewModel.put("eventos",eventoList);
		viewModel.put("hayFecha", hayFecha);
		viewModel.put("noHayEventos", noHayEventos);
		ModelAndView modelAndView = new ModelAndView(viewModel, "calendario.hbs");
		return new HandlebarsTemplateEngine().render(modelAndView);
	}
	public List<Evento> tieneSugerenciasPendientes(List<Evento> eventos,Usuario usuario) {
		List<Sugerencia> sugerencias =usuario.getSugerencias();
		sugerencias.remove(null);
		if(!sugerencias.stream().anyMatch(sugerencia->sugerencia.getEstado().equals(TipoSugerencias.PENDIENTE)))
		return null;
		return eventos.stream().filter(evento->sugerencias.stream().anyMatch(sugerencia->sugerencia.getEvento().equals(evento)&&sugerencia.getEstado().equals(TipoSugerencias.PENDIENTE))).collect(Collectors.toList());
	}
	
	private List<Evento> calcularEventos(String fechaString,Usuario usuarie){
		int diaNum = Integer.parseInt(fechaString.substring(8,10));
		int mesNum = Integer.parseInt(fechaString.substring(5,7));
		int anioNum = Integer.parseInt(fechaString.substring(0,4));
		List<Evento> eventos= new ArrayList<Evento>(usuarie.eventos());
		LocalDateTime fecha = LocalDateTime.of(anioNum,mesNum,diaNum,0,0,0);
		//agregarEvento(usuarie);
		//agregarSugerencia(usuarie);
		return eventos.stream()
				.filter(evento->((evento.getFrecuencia().equals(TipoFrecuencia.UNICO)) && sucedeEnEsteDia(fecha,evento)))
				.collect(Collectors.toList()); 
	}
	private static boolean sucedeEnEsteDia(LocalDateTime fecha,Evento evento) {
		LocalDateTime fechaEvento=evento.cualEsLaFechaProxima(fecha);
		return fechaEvento.equals(fecha)||(fechaEvento.isAfter(fecha) && fechaEvento.isBefore(fecha.plusDays(1)));
	}
	//////////////////////////////////////////SETEA_EJEMPLOS///////////////////////////////////////////////////////////////
	private void agregarEvento(Usuario usuario) {
		withTransaction(() -> {
			FrecuenciaUnicaVez frecuencia =new FrecuenciaUnicaVez(2019,5,24);
			Evento evento = new Evento(frecuencia,"Sin descripcion");
			usuario.agendarEvento(evento);
			/*entityManager().persist(frecuencia);
			entityManager().persist(evento);*/
		});
		//entityManager().close();
	}
	private void agregarSugerencia(Usuario usuario) {
		withTransaction(()->{
			FrecuenciaUnicaVez frecuencia =new FrecuenciaUnicaVez(2020,1,1);
			Evento evento = new Evento(frecuencia,"TengoUnaSugerencia");
			Set<Prenda> atuendo = new HashSet<Prenda>();
			Set<Prenda> atuendo2 = new HashSet<Prenda>();
			Prenda jean = new PrendaBuilder().conTipo(TipoPrenda.Pantalon).conTela(Material.Jean).conColorPrimario(Color.Azul).crearPrenda();
			Prenda camisaCorta = new PrendaBuilder().conTipo(TipoPrenda.CamisaMangaCorta).conTela(Material.Algodon).conColorPrimario(Color.Rojo).conColorSecundario(Color.Amarillo).crearPrenda();
			atuendo.add(jean);
			atuendo.add(camisaCorta);
			atuendo2.add(jean);
			Sugerencia sugerencia1 = new Sugerencia(atuendo,evento);
			Sugerencia sugerencia2 = new Sugerencia(atuendo2,evento);
			usuario.agendarEvento(evento);
			usuario.agregarSugerencia(sugerencia1);
			usuario.agregarSugerencia(sugerencia2);
			
			entityManager().persist(frecuencia);
			entityManager().persist(evento);
			entityManager().persist(sugerencia2);
			entityManager().persist(sugerencia1);
			entityManager().persist(jean);
			entityManager().persist(camisaCorta);
		});
		entityManager().close();
		
	}
}
