package controllers;

import domain.RepositorioDeUsuarios;
import domain.Usuario;
import spark.ModelAndView;
import spark.Response;
import spark.Request;


public class LoginController {
	public ModelAndView show(Request req, Response res) {
		if(req.session().attribute("nombreUsuario") != null) {
			res.redirect("/perfil");
			return null;
		}	
		
		return new ModelAndView(null, "login.hbs");
	}
	
	public ModelAndView login(Request req,Response res) {
		RepositorioDeUsuarios repo = RepositorioDeUsuarios.getInstance();
		String password = req.queryParams("pass");
		try {
		Usuario usuarie = repo.buscarPorNombre(req.queryParams("nombreUsuario"));
		usuarie.validarContrasenia(password);
		req.session().attribute("nombreUsuario", usuarie.getNombreUsuario()); 
		res.cookie("nombreUsuario", usuarie.getNombreUsuario());
		res.redirect("/perfil");
		}
		catch(Exception e) {
			System.out.println("Error->" + e);
			res.redirect("/");

		}
		return null;
	}
	
}
