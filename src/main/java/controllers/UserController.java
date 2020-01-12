package controllers;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import domain.*;
import domain.enums.Color;
import domain.enums.TipoUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class UserController {
	public ModelAndView showProfile(Request req, Response res) {
		Map<String,Object> viewModel = new HashMap<String, Object>();
		RepositorioDeUsuarios repo = RepositorioDeUsuarios.getInstance();
		Usuario usuarie = repo.buscarPorNombre(req.cookie("nombreUsuario"));
		String username = req.cookie("nombreUsuario");
		
		int cantidadGuardarropas = cantidadGuardarropas(usuarie);
		int cantidadPrendas = cantidadPrendas(usuarie);
		int cantidadMaximaPrendas = usuarie.getMaximoPrendas();
		TipoUsuario tipo = usuarie.getTipo();
		
		viewModel.put("nombreUsuario",username);
		viewModel.put("cantGuardarropas",cantidadGuardarropas);
		viewModel.put("cantPrendas",cantidadPrendas);
		viewModel.put("maxPrendas",cantidadMaximaPrendas);
		viewModel.put("tipo",tipo);
		
		
		
		return new ModelAndView(viewModel,"perfil.hbs");
	}
	
	private int cantidadGuardarropas(Usuario user) {
		return 	user.getGuardarropas().size();

	}
	private int cantidadPrendas(Usuario user) {
		int CantPendas = user.getGuardarropas().stream().map(g->g.cantidadDePrendasGuardadas()).mapToInt(Integer::intValue)
		  .sum();
		
		return CantPendas;
	}
}
