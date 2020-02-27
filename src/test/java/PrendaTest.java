import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

import domain.enums.*;
import domain.exceptions.*;
import domain.*;

public class PrendaTest {
	
	@Test (expected = TieneParametrosNulosException.class)
	public void noSePuedeCrearPrendasConParametrosNulos() {
		new PrendaBuilder().conTipo(TipoPrenda.Calza).crearPrenda();
	}
	
	@Test (expected = MaterialNoPermitidoException.class)
	public void noSePuedenCrearPrendasConMaterialesInconsistentes() {
		new PrendaBuilder().conTipo(TipoPrenda.Remera).conColorPrimario(Color.azul).conTela(Material.cuero).crearPrenda();
	}
	
	@Test
	public void resultaValidoLaCreacionDePrendasSinColorSecundario(){
		Prenda remeraRosa = new PrendaBuilder().conColorPrimario(Color.rosa).conTipo(TipoPrenda.Remera).conTela(Material.algodon).crearPrenda();
		assertEquals(remeraRosa.getColorSecundario(),null);
	}
	
	@Test (expected = TieneParametrosNulosException.class)
	public void noSePuedeAsignarLaTelaSinSaberElTipo(){
		new PrendaBuilder().conColorPrimario(Color.rosa).conTela(Material.algodon).conTipo(TipoPrenda.Remera).crearPrenda();
	}
	@Test 
	public void crearPrendaConAtributosNoNulos() {
		Prenda remeraRosa = new PrendaBuilder().conColorPrimario(Color.rosa).conTipo(TipoPrenda.Remera).conTela(Material.algodon).crearPrenda();
		assertTrue((remeraRosa.getColorPrimario()!=null) &&(remeraRosa.getTipo()!=null)&&(remeraRosa.getTela()!=null));
	}
	
	@Test
	public void crearUnaPrendaConFoto() throws NoSePuedeAbrirImagen {
		Foto imagenDeRemeraNegra = new Foto("src/test/java/remeranegra.jpg");
		Prenda remeraNegra = new PrendaBuilder().conColorPrimario(Color.negro).conTipo(TipoPrenda.Remera).conTela(Material.algodon).conFoto(imagenDeRemeraNegra).crearPrenda();
		assertEquals(remeraNegra.getFoto(),imagenDeRemeraNegra);
	}
	

	@Test (expected = TieneParametrosNulosException.class)
	public void noSePodranCrearPrendasSinNivelAbrigo() {
		new PrendaBuilder().conColorPrimario(Color.rosa).conTela(Material.algodon).conTipo(TipoPrenda.Remera).crearPrenda();
}
}
