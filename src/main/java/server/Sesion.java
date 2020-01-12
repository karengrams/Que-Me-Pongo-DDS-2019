package server;

public class Sesion {
	public int id_sesion;
	public String sesion;//nombreUsuario
	//se tendria que guardar username,pais y otros datos supuestamente..
	//Por ahora lo dejo asi porque no se como agregar o si hace falta si quiera.
	
	public int getId_sesion() {
		return id_sesion;
	}
	public void setId_sesion(int id_sesion) {
		this.id_sesion = id_sesion;
	}
	public String getSesion() {
		return sesion;
	}
	public void setSesion(String sesion) {
		this.sesion = sesion;
	}
}
