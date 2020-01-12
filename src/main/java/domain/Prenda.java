package domain;
import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import domain.enums.*;
@Entity
public class Prenda extends SuperClase{
	
	// ---------------------------- Atributos -------------------------------
	
	@Enumerated(EnumType.STRING)
	private Color colorPrimario;
	
	@Enumerated(EnumType.STRING)
	private Color colorSecundario;
	
	@Enumerated(EnumType.STRING)
	private TipoPrenda tipo;
	
	@Enumerated(EnumType.STRING)
	private Material tela;
	
    @Nullable @OneToOne 
	private Foto foto;
	
	private int nivelAbrigo;
	
	private boolean usada = false;
	public Prenda() {}
	// ------------------ Getters, setters y constructores ------------------
	
	public Color getColorPrimario() {
		return colorPrimario;
	}
	
	public void setColorPrimario(Color colorPrimario) {
		this.colorPrimario = colorPrimario;
	}
	
	public void setColorSecundario(Color colorSecundario) {
		this.colorSecundario = colorSecundario;
	}
	
	public Color getColorSecundario() {
		return colorSecundario;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public void setTipo(TipoPrenda tipo) {
		this.tipo = tipo;
	}

	public TipoPrenda getTipo() {
		return tipo;
	}
	
	public void setTela(Material tela) {
		this.tela = tela;
	}
	
	public Material getTela() {
		return tela;
	}

	public Foto getFoto() {
		return foto;
	}

	public void setNivelAbrigo(int nivelAbrigo) {
		this.nivelAbrigo = nivelAbrigo;
	}
	
	public int getNivelAbrigo() {
		return nivelAbrigo;
	}
	
	public void setUsada(boolean usada) {
		this.usada = usada;
	}
	
	public boolean getEsBase() {
		return this.esBase(this.getTipo()); 
	}
	
	// ------------------------------ Metodos -------------------------------

	public boolean isUsada() {
		return usada;
	}

	private boolean esBase(TipoPrenda prenda) {
		boolean aux = prenda == TipoPrenda.Remera 
				|| prenda == TipoPrenda.RemeraMangaCorta 
				|| prenda == TipoPrenda.RemeraMangaLarga
				|| prenda == TipoPrenda.CamisaMangaCorta
				|| prenda == TipoPrenda.CamisaMangaLarga;		
		return aux;
	}
	
	public boolean esDeVerano() {
		return this.tipo == TipoPrenda.Ojotas
				||this.tipo == TipoPrenda.Short
				||this.tipo == TipoPrenda.PolleraCorta
				||this.tipo == TipoPrenda.Bermuda;
		
	}

}
