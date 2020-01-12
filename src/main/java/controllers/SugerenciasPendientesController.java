package controllers;


import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import domain.Evento;
import domain.RepositorioDeUsuarios;
import domain.Usuario;
import domain.enums.TipoSugerencias;
import domain.Sugerencia;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class SugerenciasPendientesController implements WithGlobalEntityManager, TransactionalOps{	
	
	////////////METODOS_QUE_USA_POST_INICIANDO_EN_confirmarSugerencia/////////////////////////////
	private void cambiarEstados(Sugerencia sugerenciaAceptada, List<Sugerencia> sugerencias) {
		withTransaction(()->{
			sugerenciaAceptada.setEstado(TipoSugerencias.ACEPTADA);
			sugerencias.remove(sugerenciaAceptada);
			sugerencias.forEach(sug->sug.setEstado(TipoSugerencias.RECHAZADA));
			entityManager().merge(sugerenciaAceptada);
			sugerencias.forEach(sug->entityManager().merge(sug));
		});
		entityManager().close();
	}
	private boolean isNumeric(String cadena) {
		try {
			Integer.parseInt(cadena);
			return true;
		}catch (Exception e) {
			return false;
		}	
	}
	public ModelAndView confirmarSugerencia(Request req, Response res){
		String sugerenciaNum=req.queryParams("sugerencia");
		if(isNumeric(sugerenciaNum)) {
			List<Sugerencia> sugerencias = req.session().attribute("Sugerencias");
			Sugerencia sugerencia = sugerencias.get(Integer.valueOf(sugerenciaNum));
			cambiarEstados(sugerencia,sugerencias);
			res.redirect("/eventos");
			return null;
		}
		return null;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////METODOS_QUE_USA_POST_INICIANDO_EN_verSugerencias//////////////
	private boolean esDeEsteEvento(Sugerencia sugerencia,Long idEvento) {
		return sugerencia.getEvento().getId()==idEvento;
	}
	private List<Sugerencia> obtenerSugerencias(Long id,Usuario usuario) {
		List<Sugerencia> sugerencias =  usuario.getSugerencias();
		sugerencias.remove(null);
		return sugerencias.stream().filter(sugerencia->esDeEsteEvento(sugerencia,id)).collect(Collectors.toList());
	}
	public String verSugerencias(Request req, Response res){
		String id=req.params("id");
		Usuario usuario = RepositorioDeUsuarios.getInstance().buscarPorNombre(req.cookie("nombreUsuario"));
		List<Sugerencia>sugerencias = obtenerSugerencias(Long.valueOf(id),usuario);
		req.session().attribute("Sugerencias", sugerencias);
		HashMap<String, Object> viewModel = new HashMap<>();
		viewModel.put("sugerencias", sugerencias);
		viewModel.put("Evento", id);
		ModelAndView modelAndView = new ModelAndView(viewModel, "sugerenciasPendientes.hbs");
		return new HandlebarsTemplateEngine().render(modelAndView);
	}

}
