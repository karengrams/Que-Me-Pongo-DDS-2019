package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import domain.Evento;
import domain.RepositorioDeUsuarios;
import domain.Usuario;
import domain.frecuenciasDeEventos.FrecuenciaAnual;
import domain.frecuenciasDeEventos.FrecuenciaDeEvento;
import domain.frecuenciasDeEventos.FrecuenciaDiaria;
import domain.frecuenciasDeEventos.FrecuenciaMensual;
import domain.frecuenciasDeEventos.FrecuenciaSemanal;
import domain.frecuenciasDeEventos.FrecuenciaUnicaVez;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;
import tiempo.checkboxes.AnioCheckbox;
import tiempo.checkboxes.DiaCheckbox;
import tiempo.checkboxes.MesCheckbox;
import tiempo.checkboxes.SemanaCheckbox;
import tiempo.checkboxes.Tiempo;
import tiempo.checkboxes.UnicaVezCheckbox;

public class EventoController  extends AbstractPersistenceTest implements WithGlobalEntityManager{
	
	boolean todoOk = false;

	public ModelAndView mostrarAltaDeEvento(Request req, Response res) {
		return new ModelAndView(null, "altaDeEvento.hbs");
	}

	public ModelAndView elegirDescripcionYFrecuencia(Request req, Response res) {
		HashMap<String, Object> viewModel = new HashMap();
		
		String descripcion = req.queryParams("Descripcion");
		String frecuencia = req.queryParams("Frecuencia");
		
		Tiempo fecha = this.obtenerFecha(frecuencia);
		res.cookie("Descripcion", descripcion);
		
		req.session().attribute("fecha", fecha);
		
		res.redirect("/eventos/nuevo/horarios");
		return null;
	}
	
	public ModelAndView mostrarOpcionesDeFrecuencia(Request req, Response res) {
		HashMap<String, Object> viewModel = new HashMap();
		
		Tiempo fecha = req.session().attribute("fecha");
		
		viewModel.put(fecha.esPeriodico(), true);
		
		return new ModelAndView(viewModel, "elegirHorarios.hbs");
	}
	
	public ModelAndView completarFrecuencia(Request req, Response res) {
		HashMap<String, Object> viewModel = new HashMap();
		
		String descripcion = req.cookie("Descripcion");
		Tiempo fecha = req.session().attribute("fecha");
		
		if(fecha.datosIngresadosCorrectamente(req)) {
			FrecuenciaDeEvento frecuenciaDeEvento = fecha.obtenerFrecuencia();
			Evento eventoObtenido = this.armarEvento(descripcion, frecuenciaDeEvento);
			this.cargarEvento(eventoObtenido, req);
			todoOk = true;
		}
		res.redirect("/eventos/nuevo/verificacion");
		return null;
	}
	
	public ModelAndView mostrarMensajeVerificacion(Request req, Response res) {
		HashMap<String, Object> viewModel = new HashMap();
		viewModel.put("fechaIncorrecta", !todoOk);
		return new ModelAndView(viewModel, "eventoCargado.hbs");
	}
	
	public ModelAndView volverAInicio(Request req, Response res) {
		res.redirect("/");
		return null;
	}
	
	
	/*************** Metodos complementarios ***************/

	public Tiempo obtenerFecha(String frecuencia) {
		List<Tiempo> elemTiempo = new ArrayList<Tiempo>();
		elemTiempo.add(new DiaCheckbox());
		elemTiempo.add(new AnioCheckbox());
		elemTiempo.add(new SemanaCheckbox());
		elemTiempo.add(new MesCheckbox());
		elemTiempo.add(new UnicaVezCheckbox());
		elemTiempo = elemTiempo.stream()
				.filter(tiempo -> tiempo.verificarTiempo(frecuencia)).collect(Collectors.toList());
		
		return elemTiempo.get(0);
	}
	
	public Evento armarEvento(String descripcion, FrecuenciaDeEvento frecuencia) {
		return new Evento(frecuencia, descripcion);
	}

	public void cargarEvento(Evento evento, Request req) {
		withTransaction(()->{
			Usuario usuarie = RepositorioDeUsuarios.getInstance()
					.buscarPorNombre(req.cookie("nombreUsuario"));
			usuarie.agendarEvento(evento);
		});
	}
	
}
