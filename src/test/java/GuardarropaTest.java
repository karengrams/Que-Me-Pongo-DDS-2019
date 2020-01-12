import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import domain.apisClima.*;
import domain.enums.*;
import domain.exceptions.*;
import domain.*;
public class GuardarropaTest {
	OpenWeatherMapAPI weatherAPI = new OpenWeatherMapAPI();
	ProveedorClima APIDeMentiritas = new MockAPI(21,23,false);
	ProveedorClima APIDeMentiritasDeInvierno = new MockAPI(10,19,false);
	ProveedorClima APIMockInvierno = new MockAPI(10,12,false);
	ProveedorClima APIMockTemplado = new MockAPI(16,16,false);
	Usuario juan = new Usuario(TipoUsuario.PREMIUM, 0,"juan","123");
	Guardarropa armario = new Guardarropa();
	Guardarropa otroArmario = new Guardarropa();
	Prenda camisaCorta = new PrendaBuilder().conTipo(TipoPrenda.CamisaMangaCorta).conTela(Material.Algodon).conColorPrimario(Color.Rojo).conColorSecundario(Color.Amarillo).crearPrenda();
	Prenda zapatos = new PrendaBuilder().conTipo(TipoPrenda.Zapatos).conTela(Material.Cuero).conColorPrimario(Color.Amarillo).crearPrenda();
	Prenda gorra= new PrendaBuilder().conTipo(TipoPrenda.Gorra).conColorPrimario(Color.Negro).conTela(Material.Algodon).crearPrenda();
	Prenda camisaLarga = new PrendaBuilder().conTipo(TipoPrenda.CamisaMangaLarga).conColorPrimario(Color.Blanco).conTela(Material.Saten).crearPrenda();
	Prenda ojotas = new PrendaBuilder().conTipo(TipoPrenda.Ojotas).conTela(Material.Caucho).conColorPrimario(Color.Negro).crearPrenda();
	Prenda jean = new PrendaBuilder().conTipo(TipoPrenda.Pantalon).conTela(Material.Jean).conColorPrimario(Color.Azul).crearPrenda();
	Prenda camperaGucci = new PrendaBuilder().conTipo(TipoPrenda.Campera).conTela(Material.Algodon).conColorPrimario(Color.Negro).conAbrigo(2).crearPrenda();
	Prenda botas = new PrendaBuilder().conTipo(TipoPrenda.Zapatos).conTela(Material.Cuero).conColorPrimario(Color.Amarillo).conAbrigo(3).crearPrenda();
	Prenda pantalonAbrigo = new PrendaBuilder().conTipo(TipoPrenda.Pantalon).conTela(Material.Jean).conColorPrimario(Color.Azul).conAbrigo(3).crearPrenda();
	Prenda buzo =  new PrendaBuilder().conTipo(TipoPrenda.Buzo).conTela(Material.Algodon).conColorPrimario(Color.Rosa).conColorSecundario(Color.Amarillo).conAbrigo(2).crearPrenda();
	@Before
	public void setUp(){
		juan.agregarGuardarropa(armario);
		juan.agregarGuardarropa(otroArmario);
		juan.cargarPrenda(armario, camisaCorta);
		juan.cargarPrenda(armario,zapatos);
		juan.cargarPrenda(armario,gorra);
		juan.cargarPrenda(armario,camperaGucci);
	}


	@Test (expected = YaSeEncuentraCargadaException.class)
	public void entreArmariosNoSePuedeCompartirPrenda() {
		juan.cargarPrenda(otroArmario,camisaCorta);
	}

	@Test
	public void elArmarioNoDebeDevolverAtuendosSiNoTieneUnaPrendaPorCadaCategoria() {
		assertTrue(armario.pedirAtuendosSegun(APIDeMentiritas,juan).isEmpty());
	}
	
	@Test (expected = YaSeEncuentraCargadaException.class)
	public void noPuedenHaberPrendasRepetidasEnUnArmario() {
		juan.cargarPrenda(armario, camisaCorta);
		juan.cargarPrenda(armario, camisaCorta);

	}
	
	@Test
	public void siJuanSolicitaSusAtuendosSeObtendranDosConOSinAccesorio() {
		HashSet<Prenda> atuendo = new HashSet<Prenda>(Arrays.asList(jean,gorra,zapatos,camisaCorta));
		HashSet<Prenda> atuendo2 = new HashSet<Prenda>(Arrays.asList(jean,zapatos,camisaCorta));
//		camisaCorta.setEsBase(true);//esto hay que cambiar despues
		juan.cargarPrenda(armario, jean);
		assertTrue(armario.pedirAtuendosSegun(APIDeMentiritas,juan).contains(atuendo));
		assertTrue(armario.pedirAtuendosSegun(APIDeMentiritas,juan).contains(atuendo2));
	}
	@Test
	public void siJuanCalificaFrioEnLosPiesNoDarleOjotas() {
		HashSet<Prenda> atuendo = new HashSet<Prenda>(Arrays.asList(jean,gorra,zapatos,camisaCorta));
		HashSet<Prenda> atuendo2 = new HashSet<Prenda>(Arrays.asList(jean,ojotas,camisaCorta));
		juan.calificar(Categoria.Calzado,TipoSensaciones.FRIO);
		juan.calificar(Categoria.Calzado,TipoSensaciones.FRIO);
		juan.calificar(Categoria.Calzado,TipoSensaciones.FRIO);


		juan.cargarPrenda(armario, jean);
		juan.cargarPrenda(armario,ojotas);
		assertTrue(armario.pedirAtuendosSegun(APIDeMentiritas,juan).contains(atuendo));
		assertFalse(armario.pedirAtuendosSegun(APIDeMentiritas,juan).contains(atuendo2));
	}
	@Test
	public void calificacionesFrio() {
		juan.calificar(Categoria.Calzado,TipoSensaciones.FRIO);
		juan.calificar(Categoria.Calzado,TipoSensaciones.FRIO);
		juan.calificar(Categoria.Calzado,TipoSensaciones.FRIO);
		juan.calificar(Categoria.Calzado,TipoSensaciones.FRIO);
		assertEquals(juan.getCalificaciones().size(),4);

	}
	@Test
	public void siJuanCalificaFrioEnTorsoDarleMasAbrgio() {
		HashSet<Prenda> atuendo = new HashSet<Prenda>(Arrays.asList(pantalonAbrigo,buzo,botas,camisaCorta,camperaGucci));
		HashSet<Prenda> atuendo2 = new HashSet<Prenda>(Arrays.asList(pantalonAbrigo,botas,camisaCorta,camperaGucci));
		juan.cargarPrenda(armario, pantalonAbrigo);
		juan.cargarPrenda(armario, botas);
		juan.cargarPrenda(armario,buzo);
		juan.calificar(Categoria.Superior,TipoSensaciones.FRIO);

		assertTrue(armario.pedirAtuendosSegun(APIMockInvierno,juan).contains(atuendo));
		assertFalse(armario.pedirAtuendosSegun(APIMockInvierno,juan).contains(atuendo2));
	}
	@Test
	public void siJuanCalificaFrioEnPantalonesDarleMasAbrigoYCalorEnSuperiorDarleMenosAbrigo() {
		HashSet<Prenda> atuendo = new HashSet<Prenda>(Arrays.asList(pantalonAbrigo,botas,camisaCorta,camperaGucci));
		HashSet<Prenda> atuendo2 = new HashSet<Prenda>(Arrays.asList(jean,botas,camisaCorta,camperaGucci,buzo));
		HashSet<Prenda> atuendo3 = new HashSet<Prenda>(Arrays.asList(pantalonAbrigo,botas,camisaCorta,camperaGucci,buzo));

		juan.cargarPrenda(armario, pantalonAbrigo);
		juan.cargarPrenda(armario, botas);
		juan.cargarPrenda(armario, jean);
		juan.cargarPrenda(armario, buzo);
		juan.calificar(Categoria.Inferior,TipoSensaciones.FRIO);
		
		juan.calificar(Categoria.Superior,TipoSensaciones.CALOR);
		List<Set<Prenda>> test = armario.pedirAtuendosSegun(APIMockTemplado, juan);
		int i;
		assertTrue(armario.pedirAtuendosSegun(APIMockTemplado,juan).contains(atuendo));
		assertFalse(armario.pedirAtuendosSegun(APIMockTemplado,juan).contains(atuendo2));
		assertFalse(armario.pedirAtuendosSegun(APIMockTemplado,juan).contains(atuendo3));
		
	}
	
	@Test
	public void seAgregaUnGuardarropaADosUsuarios() {
		Usuario lara = new Usuario(TipoUsuario.PREMIUM,0,"lara","123");
		lara.agregarGuardarropa(armario);
	}
	
}
