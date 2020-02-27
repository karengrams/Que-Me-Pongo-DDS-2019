package controllers;

  import java.util.Arrays;
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
  	Boolean error = false;
  	Boolean creacion_exitosa = false;
  	Boolean guardarropas_cargado = false;
  	Usuario usuarie;

  	public ModelAndView show(Request req, Response res) {
  		Map<String,Object> viewModel = new HashMap<String, Object>();
  		RepositorioDeUsuarios repo = RepositorioDeUsuarios.getInstance();
  		usuarie = repo.buscarPorNombre(req.cookie("nombreUsuario"));
  		List<Guardarropa> guardarropas = usuarie.getGuardarropas().stream().collect(Collectors.toList());
  		error=false;
  		creacion_exitosa=false;
  		viewModel.put("error",error);
  		viewModel.put("creacion_exitosa",creacion_exitosa);
  		viewModel.put("guardarropas", guardarropas);
  		viewModel.put("colores",Arrays.asList(Color.values()));
  		return new ModelAndView(viewModel, "cargarprenda.hbs");
  	}

  	public ModelAndView load(Request req, Response res) {
  		Map<String,Object> viewModel = new HashMap<String, Object>();
  		String material_de_prenda = req.queryParams("tela");
  		String color_primario = req.queryParams("colorPrimario");
  		String color_secundario = req.queryParams("colorSecundario");
  		String nro_de_guardarropa = req.queryParams("guardarropa");

  		try {
  			Material material = Material.valueOf(material_de_prenda);
  			builder.conTela(material);
  			Color colorprimario = Color.valueOf(color_primario);
  			builder.conColorPrimario(colorprimario);
  			Color colorsecundario = Color.valueOf(color_secundario);
  			builder.conColorSecundario(colorsecundario);
  			res.cookie("idguardarropa", nro_de_guardarropa);
  			res.redirect("/prenda/nueva/verificacion");
  		}
  		catch(Exception e) {
  			error =true;
  			creacion_exitosa=false;
  			viewModel.put("error",error);
  			viewModel.put("msg_exception",e.getMessage());
  			res.redirect("/prenda/nueva/verificacion");
  		}
  		return new ModelAndView(viewModel, "cargarprenda.hbs");
  	}

  	public ModelAndView confirmation(Request req, Response res) {
  		Map<String,Object> viewModel = new HashMap<String, Object>();
  		RepositorioDeUsuarios repo = RepositorioDeUsuarios.getInstance();
  		Usuario usuarie = repo.buscarPorNombre(req.cookie("nombreUsuario"));
  		List<Guardarropa> guardarropas = usuarie.getGuardarropas().stream().collect(Collectors.toList());
  		viewModel.put("guardarropas", guardarropas);
  		viewModel.put("colores",Arrays.asList(Color.values()));
  		try{
  			int id_guardarropa = Integer.parseInt(req.cookie("idguardarropa"));
  			Guardarropa guardarropa = usuarie.buscarGuardarropa(id_guardarropa);
  			Prenda prenda = builder.crearPrenda();
  			withTransaction(() -> {
  				usuarie.cargarPrenda(guardarropa, prenda);
  			});

  			builder = new PrendaBuilder();// sino devuelve siempre la misma prenda

  			creacion_exitosa = true;
  			error = false;
  			viewModel.put("error",error);
  			viewModel.put("creacion_exitosa",creacion_exitosa);
  			return new ModelAndView(viewModel, "verificacionPrenda.hbs");

  		}catch (Exception e) {
  			error = true;
  			creacion_exitosa= false;
  			viewModel.put("error",error);
  			viewModel.put("creacion_exitosa",creacion_exitosa);
  			viewModel.put("msg_exception",e.getMessage());
  			return new ModelAndView(viewModel, "verificacionPrenda.hbs");
  		}
  	}

  	public ModelAndView getClotheType(Request req, Response res) {
  		Map<String, Object> viewModel = new HashMap<String, Object>();
  		String tipo = req.queryParams("tipoPrenda");
  		TipoPrenda tipo_de_prenda = TipoPrenda.valueOf(tipo);

  		List<String> materiales = tipo_de_prenda.materialesPermitidos.stream().map(mat -> mat.toString())
  				.collect(Collectors.toList());

  		Gson gson = new GsonBuilder().create();
  		String jsonArray = gson.toJson(materiales);
  		res.body(jsonArray);
  		builder.conTipo(tipo_de_prenda);
  		return new ModelAndView(viewModel, "cargarprenda.hbs");
  }
  	
  	public ModelAndView getWardrobeID(Request req, Response res) {
  		Map<String, Object> viewModel = new HashMap<String, Object>();
  		String idguardarropa = req.queryParams("guardarropa");
  		Guardarropa guardarropa = usuarie.buscarGuardarropa(Integer.parseInt(idguardarropa));
  		Gson gson = new GsonBuilder().create();
  		String jsonArray = gson.toJson(guardarropa.listPrendas());
  		res.body(jsonArray);
  		return new ModelAndView(viewModel, "cargarprenda.hbs");
  }

  	private int buscarGuardarropaPorNombre(Usuario user, String name) {
  		List<Long> filtro = user.getGuardarropas().stream().filter(g -> g.nombre.equals(name)).map(h -> h.getId())
  				.collect(Collectors.toList());
  		int resultado = filtro.get(0).intValue();
  		return resultado;
  	}
  }
