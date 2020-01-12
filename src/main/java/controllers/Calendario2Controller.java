package controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import domain.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class Calendario2Controller {
	public ModelAndView test(Request req, Response res) {

		Usuario usuario = RepositorioDeUsuarios.getInstance().buscarPorNombre(req.cookie("nombreUsuario"));
		Set<Evento> listaEventos=usuario.eventosProximos(LocalDateTime.of(
								LocalDate.now(),
								LocalTime.now())
								);
		HashMap<String, Object> viewModel = new HashMap<>();
		viewModel.put("listaEventos",listaEventos);
		ModelAndView modelAndView = new ModelAndView(viewModel, "calendar.hbs");
		
		return modelAndView;
	}
	
	public ModelAndView busquedaPorFecha(Request req,Response res) {
		Usuario usuario = RepositorioDeUsuarios.getInstance().buscarPorNombre(req.cookie("nombreUsuario"));
		//poner fecha y buscar lista de eventos por fecha
		Set<Evento> listaEventos=usuario.eventosProximos(LocalDateTime.of(
								LocalDate.now(),
								LocalTime.now())
								);
		HashMap<String, Object> viewModel = new HashMap<>();
		viewModel.put("listaEventos",listaEventos);
		ModelAndView modelAndView = new ModelAndView(viewModel, "calendarBusquedaFecha.hbs");
		return modelAndView;
	}
	
}
