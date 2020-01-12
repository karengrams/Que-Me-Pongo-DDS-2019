import org.junit.Before;
import domain.*;
import domain.apisClima.MockAPI;
import domain.apisClima.ProveedorClima;
import domain.enums.TipoUsuario;
import domain.frecuenciasDeEventos.FrecuenciaUnicaVez;

public class UITest {
	QueMePongoModel model = new QueMePongoModel();
	ProveedorClima APIDeMentiritas = new MockAPI(21,23,false);
	Evento evento = new Evento(new FrecuenciaUnicaVez(2019,5,24),"Ir a cenar");
	Evento evento2 = new Evento(new FrecuenciaUnicaVez(2019,8,1),"Ir a cenar");
	Usuario juan = new Usuario(TipoUsuario.PREMIUM,0,"juan","123");
	
	@Before
	public void setUp() {
		juan.agendarEvento(evento);
		juan.agendarEvento(evento2);
	}
	/*
	@Test (expected = UserException.class)
	public void NoSePuedenIngresarFechasInvalidas() {
		model.setFechaFin(201900529);
		model.setFechaInicio(20190801);
		model.listarEventos();
	}*/
	/*
	@Test (expected = UserException.class)
	public void NoSePuedeIngresarComoFechaInicialUnaFechaPosteriorALaFechaFinal() {
		model.setFechaFin(20190520);
		model.setFechaInicio(20190524);
		model.listarEventos();
	}*/
	
	
}
