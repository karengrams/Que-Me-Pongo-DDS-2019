package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import domain.exceptions.*;
import domain.enums.*;

public class PrendaBuilder{
	
	Prenda prenda = new Prenda();
	
	public Prenda prenda() {
		return prenda;
	}

	public Prenda crearPrenda(){
		if(this.tieneAlgunParametroNulo()) {
			throw new TieneParametrosNulosException("No se pudo crear la prenda ya que tiene parametros sin instanciar.");
		}
		return prenda;
	}
	
	public PrendaBuilder conColorPrimario(Color colorPrimario){
		prenda.setColorPrimario(colorPrimario);
		return this;
	}
	
	public PrendaBuilder conColorSecundario(Color colorSecundario){
		prenda.setColorSecundario(colorSecundario);
		return this;
	}
	
	public PrendaBuilder conTipo(TipoPrenda tipo){
		prenda.setTipo(tipo);
		return this;
	}
	
	public PrendaBuilder conTela(Material tela){
		if(prenda.getTipo() == null) {
			throw new TieneParametrosNulosException("Debe definir primero el tipo.");
		}
		if(!prenda.getTipo().materialesPermitidos.contains(tela)) {
			throw new MaterialNoPermitidoException("El material " + tela + " no es valido para el tipo de prenda ingresado.");
		}
		this.prenda.setTela(tela);
		return this;
	}
	
	public PrendaBuilder conFoto(Foto foto){
		prenda.setFoto(foto);
		return this;
	}
	
	public boolean tieneAlgunParametroNulo(){ //HAY QUE MODIFICAR Y AGREGAR LA BASE Y ABRIGO ACA
		List parametros = new ArrayList(Arrays.asList(prenda.getTela(),prenda.getColorPrimario(),prenda.getTipo()));
		return parametros.stream().anyMatch(parametro -> parametro == null);
	}

}
