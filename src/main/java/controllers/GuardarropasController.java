package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import domain.Guardarropa;
import domain.RepositorioDeUsuarios;
import domain.Prenda;
import domain.PrendaBuilder;
import domain.Usuario;
import domain.enums.Color;
import domain.enums.Material;
import domain.enums.TipoPrenda;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class GuardarropasController {
	
	public ModelAndView show(Request req, Response res) {
		Map<String, Object> viewModel = new HashMap<String, Object>();		
		RepositorioDeUsuarios repo = RepositorioDeUsuarios.getInstance();
		Usuario usuarie = repo.buscarPorNombre(req.cookie("nombreUsuario"));
		List<Guardarropa> guardarropas = usuarie.getGuardarropas().stream().collect(Collectors.toList());
		Boolean hayGuardarropas = !guardarropas.isEmpty();
		viewModel.put("hayGuardarropas", hayGuardarropas);
		viewModel.put("guardarropas", guardarropas);
		
		return new ModelAndView(viewModel,"guardarropa.hbs");
	}
	
	public ModelAndView pru(Request req, Response res) {
		Map<String,Object> viewModel = new HashMap<String, Object>();
		RepositorioDeUsuarios repo = RepositorioDeUsuarios.getInstance();
		Usuario usuarie = repo.buscarPorNombre(req.cookie("nombreUsuario"));
		List<Guardarropa> listaGuard = usuarie.getGuardarropas().stream().collect(Collectors.toList());

		viewModel.put("guardarropas", listaGuard);
		
		return new ModelAndView(viewModel,"listaGuardarropas.hbs");
	}

}
