import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import domain.apisClima.*;
import domain.enums.*;
import domain.frecuenciasDeEventos.*;
import domain.*;
import java.time.*;

public class EventoTest {
	ProveedorClima APIDeMentiritas = new MockAPI(21,23,false);
	Usuario juan = new Usuario(TipoUsuario.PREMIUM,15,"juan32","123");
	Guardarropa armario = new Guardarropa();
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
	Evento eventoLoco = new Evento(new FrecuenciaUnicaVez(2019,2,16),"Sin descripcion");//la fecha es:"16-02-2019"
	Evento eventoConFrecuenciaUnica = new Evento(new FrecuenciaUnicaVez(2019,5,24),"Sin descripcion");//"24-05-2019"
	Evento eventoConFrecuenciaDiaria = new Evento(new FrecuenciaDiaria(0),"Sin descripcion");//"16-01-2019"
	Evento eventoConFrecuenciaSemanal = new Evento(new FrecuenciaSemanal(3),"Sin descripcion");//"16-01-2019" MIERCOLES
	Evento eventoConFrecuenciaMensual = new Evento(new FrecuenciaMensual(16),"Sin descripcion");//"16-01-2019"
	Evento eventoConFrecuenciaAnual = new Evento(new FrecuenciaAnual(2,16),"Sin descripcion");//"16-01-2019"

	@Before
	public void setUp(){
		juan.agregarGuardarropa(armario);
		juan.cargarPrenda(armario, camisaCorta);
		juan.cargarPrenda(armario, zapatos);
		juan.cargarPrenda(armario, gorra);
		juan.cargarPrenda(armario, camisaLarga);
		juan.cargarPrenda(armario, ojotas);
		juan.cargarPrenda(armario, jean);
		Sugeridor.getInstance().setProveedorDeClima(APIDeMentiritas);
	}
	@After
	public void after(){
		armario.borrarPrendas();
		juan = new Usuario(TipoUsuario.PREMIUM,15,"juan32","123");
		armario = new Guardarropa();

	}
	
	@Test
	public void ProximidadEntreFechasDiferentesLajanasDevuelveFalso() {
		assertFalse(eventoConFrecuenciaUnica.esProximo(LocalDateTime.of(2019, Month.MAY, 16,0,0,0)));
		assertFalse(eventoConFrecuenciaUnica.esProximo(
				LocalDateTime.of(
						LocalDate.of(2018, Month.MAY, 24),
						LocalTime.now())
				)
		);
	}
	
	@Test 
	public void siUnEventoPideSugerenciasParaJuanElMismoTendra12Sugerencias(){
		eventoLoco.sugerir(juan);
		assertEquals(juan.getSugerencias().size(),12); //Revisar esto que me parece raro
	}
	
	@Test 
	public void CrearEventoDiarioCuandoFaltenOchoHorasTieneQueSerProximo(){//independientemente del mes a�o etc
		LocalDateTime _20190215 = LocalDateTime.of(2019, Month.FEBRUARY, 15, 23, 0);
		LocalDateTime _20180115 = LocalDateTime.of(2018, Month.JANUARY,15,23,0);
		LocalDateTime _20190123 = LocalDateTime.of(2019, Month.JANUARY,23,23,0);
		LocalDateTime _20190116 = LocalDateTime.of(2019, Month.JANUARY,16,1,0);
		LocalDateTime _20190116bis = LocalDateTime.of(2019, Month.JANUARY,16,16,0);

		assertTrue(eventoConFrecuenciaDiaria.esProximo(_20190215));
		assertTrue(eventoConFrecuenciaDiaria.esProximo(_20180115)); // 23hs
		assertTrue(eventoConFrecuenciaDiaria.esProximo(_20190123)); // 23hs
		assertFalse(eventoConFrecuenciaDiaria.esProximo(_20190116)); // 1hs
		assertTrue(eventoConFrecuenciaDiaria.esProximo(_20190116bis)); // 16hs
	}
	
	@Test 
	public void CrearEventoSemanalCuandoFaltenDosDiasTieneQueSerProximo(){//independientemente del mes a�o etc
		LocalDateTime _20190213 = LocalDateTime.of(2019, Month.FEBRUARY, 13,0, 0, 0);//MIERCOLES
		LocalDateTime _20190807 = LocalDateTime.of(2019, Month.AUGUST, 7, 23, 0);
		LocalDateTime _20180116 = LocalDateTime.of(2018, Month.JANUARY,16,23,0);
		LocalDateTime _20190123 = LocalDateTime.of(2019, Month.JANUARY,23,23,0);
		LocalDateTime _20190114 = LocalDateTime.of(2019, Month.JANUARY,14,1,0);
		LocalDateTime _20190118 = LocalDateTime.of(2019, Month.JANUARY,18,16,0);
		assertTrue(eventoConFrecuenciaSemanal.esProximo(_20190213));
		assertTrue(eventoConFrecuenciaSemanal.esProximo(_20190807));
		assertTrue(eventoConFrecuenciaSemanal.esProximo(_20180116));
		assertTrue(eventoConFrecuenciaSemanal.esProximo(_20190123));
		assertTrue(eventoConFrecuenciaSemanal.esProximo(_20190114));
		assertFalse(eventoConFrecuenciaSemanal.esProximo(_20190118));
	}
	@Test 
	public void CrearEventoMesualCuandoFaltenCincoDiasTieneQueSerProximo(){
		LocalDateTime _20190216 = LocalDateTime.of(2019, Month.FEBRUARY, 16, 23, 0);
		LocalDateTime _20180116 = LocalDateTime.of(2018, Month.JANUARY,16,23,0);
		LocalDateTime _20190111 = LocalDateTime.of(2019, Month.JANUARY,11,1,0);
		LocalDateTime _20190118 = LocalDateTime.of(2019, Month.JANUARY,18,16,0);
	
		assertTrue(eventoConFrecuenciaMensual.esProximo(_20190216));
		assertTrue(eventoConFrecuenciaMensual.esProximo(_20180116));
		assertTrue(eventoConFrecuenciaMensual.esProximo(_20190111));
		assertFalse(eventoConFrecuenciaMensual.esProximo(_20190118));
	}
	
	@Test 
	public void CrearEventoAnualCuandoFaltenTreintaDiasTieneQueSerProximo(){
		LocalDateTime _20180216 = LocalDateTime.of(2018, Month.FEBRUARY,16,0,0,0);
		LocalDateTime _20190128 = LocalDateTime.of(2019, Month.JANUARY,28,0,0,0);
		LocalDateTime _20250211 = LocalDateTime.of(2025, Month.FEBRUARY,11,0,0,0);
		LocalDateTime _20190118 = LocalDateTime.of(2019, Month.FEBRUARY,18,0,0,0);

		assertTrue(eventoConFrecuenciaAnual.esProximo(_20180216));
		assertTrue(eventoConFrecuenciaAnual.esProximo(_20190128));
		assertTrue(eventoConFrecuenciaAnual.esProximo(_20250211));
		assertFalse(eventoConFrecuenciaAnual.esProximo(_20190118));
	}
	
}
