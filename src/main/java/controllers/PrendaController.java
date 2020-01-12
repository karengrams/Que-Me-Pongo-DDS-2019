package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import domain.Guardarropa;
import domain.Prenda;
import domain.PrendaBuilder;
import domain.RepositorioDeUsuarios;
import domain.Usuario;
import domain.enums.Color;
import domain.enums.Material;
import domain.enums.TipoPrenda;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class PrendaController implements WithGlobalEntityManager, TransactionalOps {
	PrendaBuilder builder = new PrendaBuilder();

	public ModelAndView show(Request req, Response res) {
		return new ModelAndView(null, "cargarprenda.hbs");
	}

	public void cargarPrenda(Prenda prenda, Usuario user, Guardarropa guar) {
		withTransaction(() -> {
			user.cargarPrenda(guar, prenda);
		});
	}

	public ModelAndView getClotheType(Request req, Response res) {

		String tipo = req.queryParams("tipoPrenda");
		TipoPrenda tipo_de_prenda = TipoPrenda.valueOf(tipo);

		List<String> materiales = tipo_de_prenda.materialesPermitidos.stream().map(mat -> mat.toString())
				.collect(Collectors.toList());
		
		Gson gson = new GsonBuilder().create();
		String jsonArray = gson.toJson(materiales);

		System.out.println(jsonArray);
		res.body(jsonArray);
		
		return null;
}

// No se si todo lo de abajo funciona, pero tampoco voy a eliminarlo hasta que este segura

	public ModelAndView prueba(Request req, Response res) {
		Map<String, Object> viewModel = new HashMap<String, Object>();
		RepositorioDeUsuarios repo = RepositorioDeUsuarios.getInstance();
		Usuario usuarie = repo.buscarPorNombre(req.cookie("nombreUsuario"));
		List<Guardarropa> guardarropas = usuarie.getGuardarropas().stream().collect(Collectors.toList());
		viewModel.put("guardarropas", guardarropas);

		return new ModelAndView(viewModel, "eleccionGuardarropa.hbs");
	}

	public ModelAndView pruebaPost(Request req, Response res) {
		res.cookie("idGuardarropa", req.queryParams("nroGuardarropa"));
		res.redirect("/prendas/cargaDatos");

		return null;
	}

	public ModelAndView showCargaDatos(Request req, Response res) {
		Map<String, Object> viewModel = new HashMap<String, Object>();
		RepositorioDeUsuarios repo = RepositorioDeUsuarios.getInstance();
		Usuario usuarie = repo.buscarPorNombre(req.cookie("nombreUsuario"));
		List<Guardarropa> guardarropas = usuarie.getGuardarropas().stream().collect(Collectors.toList());
		viewModel.put("guardarropas", guardarropas);
		return new ModelAndView(viewModel, "wizardPrenda.hbs");
	}

	public ModelAndView saveCargaDatos(Request req, Response res) {
		RepositorioDeUsuarios repo = RepositorioDeUsuarios.getInstance();
		Usuario usuarie = repo.buscarPorNombre(req.cookie("nombreUsuario"));
		String tipo = req.queryParams("tipoPrenda");
		String colorP = req.queryParams("colorPrimario");
		String colorS = req.queryParams("colorSecundario");
		String material_de_prenda = req.queryParams("tela");
		String abrigo = req.queryParams("nivelAbrigo");
		String guardarropaSel = req.queryParams("guardarropaSel");
		TipoPrenda tipo_de_prenda = TipoPrenda.valueOf(tipo);
		builder.conTipo(tipo_de_prenda);
		Color colorPrimario = Color.valueOf(colorP);
		builder.conColorPrimario(colorPrimario);
		if (!colorS.equals("SinColor")) {
			Color colorSecundario = Color.valueOf(colorS);
			builder.conColorSecundario(colorSecundario);
		} else {
			builder.conColorSecundario(null);
		}
		Material material = Material.valueOf(material_de_prenda);
		builder.conTela(material);
		builder.conAbrigo(Integer.parseInt(abrigo));

		int id_guardarropa = buscarGuardarropaPorNombre(usuarie, guardarropaSel);
		Guardarropa guardarropa = usuarie.buscarGuardarropa(id_guardarropa);
		Prenda prenda = builder.crearPrenda();

		this.cargarPrenda(prenda, usuarie, guardarropa);
		builder = new PrendaBuilder();// sino devuelve siempre la misma prenda

		res.redirect("/perfil");
		return null;
	}

	private int buscarGuardarropaPorNombre(Usuario user, String name) {
		List<Long> filtro = user.getGuardarropas().stream().filter(g -> g.nombre.equals(name)).map(h -> h.getId())
				.collect(Collectors.toList());
		int resultado = filtro.get(0).intValue();
		return resultado;
	}
}