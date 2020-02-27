import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import domain.Evento;
import domain.Guardarropa;
import domain.JobsUsuarios;
import domain.Prenda;
import domain.PrendaBuilder;
import domain.RepositorioDeUsuarios;
import domain.Sugeridor;
import domain.Usuario;
import domain.apisClima.MockAPI;
import domain.apisClima.ProveedorClima;
import domain.enums.Color;
import domain.enums.Material;
import domain.enums.TipoPrenda;
import domain.enums.TipoUsuario;
import domain.frecuenciasDeEventos.FrecuenciaUnicaVez;

public class Evento2Test extends AbstractPersistenceTest implements WithGlobalEntityManager{
	int id;
	ProveedorClima APIDeMentiritas = new MockAPI(21,23,false);
	Usuario juan = new Usuario(TipoUsuario.PREMIUM,15,"giornoGiovanna","123");
	Guardarropa armario = new Guardarropa();
	Prenda camisaCorta = new PrendaBuilder().conTipo(TipoPrenda.CamisaMangaCorta).conTela(Material.algodon).conColorPrimario(Color.rojo).conColorSecundario(Color.amarillo).crearPrenda();
	Prenda ojotas = new PrendaBuilder().conTipo(TipoPrenda.Ojotas).conTela(Material.caucho).conColorPrimario(Color.negro).crearPrenda();
	Prenda jean = new PrendaBuilder().conTipo(TipoPrenda.Pantalon).conTela(Material.jean).conColorPrimario(Color.azul).crearPrenda();
	Prenda zapatos = new PrendaBuilder().conTipo(TipoPrenda.Zapatos).conTela(Material.cuero).conColorPrimario(Color.amarillo).crearPrenda();
	Evento eventoConFrecuenciaUnica = new Evento(new FrecuenciaUnicaVez(2019,12,5),"parcial");


	@Before
	public void setUp(){
		armario.setNombre("TestEvento2");
		juan.agregarGuardarropa(armario);
		armario.cargarPrenda(camisaCorta);
		armario.cargarPrenda(zapatos);
		armario.cargarPrenda(jean);
		Sugeridor.getInstance().setProveedorDeClima(APIDeMentiritas);
	}
	
	@Test
	public void test1() {
		List<Guardarropa> guar = new ArrayList(juan.getGuardarropas());
		assertTrue(guar.get(0).cantidadDePrendasGuardadas() == 3);	
	}
	@Test
	public void proximidadEventoConFrecuenciaUnica() {
		assertTrue(
				!eventoConFrecuenciaUnica.esProximo(
						LocalDateTime.of(
								LocalDate.of(2019, Month.DECEMBER, 1),
								LocalTime.now())
						)
				);
		assertTrue(
				eventoConFrecuenciaUnica.esProximo(
						LocalDateTime.of(
								LocalDate.of(2019, Month.DECEMBER, 4),
								LocalTime.now())
						)
				);
	}
	@Test
	public void JobdaSugerenciascuandoAcercaElEvento() {
		juan.agendarEvento(eventoConFrecuenciaUnica);
		withTransaction(() -> {
				RepositorioDeUsuarios.getInstance().agregar(juan);
			}
		);
		
		JobsUsuarios job = new JobsUsuarios();
		job.setFechaTest(LocalDateTime.of(
								LocalDate.of(2019, Month.DECEMBER, 3),
								LocalTime.now())
						);
		job.runTest();
		
		Usuario giorno = RepositorioDeUsuarios.getInstance().buscarPorNombre("giornoGiovanna");
	

		
		assertTrue(giorno.getSugerencias().size()==1);
	}

}