package domain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.persistence.Transient;
import domain.exceptions.NoSePuedeAbrirImagen;

@Entity
public class Foto extends SuperClase {

	private String ruta;
	
	public Foto(String ruta){
		this.ruta=ruta.toString();
	}

}
