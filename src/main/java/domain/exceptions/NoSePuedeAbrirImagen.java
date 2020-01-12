package domain.exceptions;

public class NoSePuedeAbrirImagen extends RuntimeException{
	public NoSePuedeAbrirImagen(String msg) {
		super(msg);
	}
}