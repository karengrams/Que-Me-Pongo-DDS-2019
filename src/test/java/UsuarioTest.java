import static org.junit.Assert.*;
import java.util.stream.*;
import java.util.*;
import org.junit.*;
import domain.apisClima.*;
import domain.frecuenciasDeEventos.*;
import domain.enums.*;
import domain.exceptions.*;
import domain.*;

public class UsuarioTest {

	ProveedorClima weatherAPI = new OpenWeatherMapAPI();
	ProveedorClima APIDeMentiritas = new MockAPI(21,23,false);
	Usuario juan = new Usuario(TipoUsuario.PREMIUM,0,"juan","123");
	Guardarropa armario = new Guardarropa();
	Guardarropa otroArmario = new Guardarropa();
	Prenda camisaCorta = new PrendaBuilder().conTipo(TipoPrenda.CamisaMangaCorta).conTela(Material.algodon).conColorPrimario(Color.rojo).conColorSecundario(Color.amarillo).crearPrenda();
	Prenda zapatos = new PrendaBuilder().conTipo(TipoPrenda.Zapatos).conTela(Material.cuero).conColorPrimario(Color.amarillo).crearPrenda();
	Prenda zapatillas = new PrendaBuilder().conTipo(TipoPrenda.Zapatillas).conTela(Material.cuero).conColorPrimario(Color.amarillo).crearPrenda();
	Prenda gorra= new PrendaBuilder().conTipo(TipoPrenda.Gorra).conColorPrimario(Color.negro).conTela(Material.algodon).crearPrenda();
	Prenda sombrero= new PrendaBuilder().conTipo(TipoPrenda.Gorra).conColorPrimario(Color.negro).conTela(Material.algodon).crearPrenda();
	Prenda camisaLarga = new PrendaBuilder().conTipo(TipoPrenda.CamisaMangaLarga).conColorPrimario(Color.blanco).conTela(Material.saten).crearPrenda();
	Prenda camisaDeLara = new PrendaBuilder().conTipo(TipoPrenda.CamisaMangaLarga).conColorPrimario(Color.blanco).conTela(Material.saten).crearPrenda();
	Prenda ojotas = new PrendaBuilder().conTipo(TipoPrenda.Ojotas).conTela(Material.caucho).conColorPrimario(Color.negro).crearPrenda();
	Prenda jean = new PrendaBuilder().conTipo(TipoPrenda.Pantalon).conTela(Material.jean).conColorPrimario(Color.azul).crearPrenda();
	Prenda pantalon = new PrendaBuilder().conTipo(TipoPrenda.Pantalon).conTela(Material.jean).conColorPrimario(Color.blanco).crearPrenda();
	Evento eventoConFrecuenciaUnica = new Evento(new FrecuenciaUnicaVez(2019,2,16),"Sin descripcion");//Fecha "16-02-2019" -> Es decir, un evento finalizado
	
	@Before
	public void setUp(){
		juan.agregarGuardarropa(armario);
		juan.setMaximoDePrendas(50);
		juan.cargarPrenda(armario, camisaCorta);
		juan.cargarPrenda(armario, zapatos);
		juan.cargarPrenda(armario, gorra);
		juan.cargarPrenda(armario, camisaLarga);
		juan.cargarPrenda(armario, ojotas);
		Sugeridor.getInstance().setProveedorDeClima(APIDeMentiritas);
	}

	@Test
	public void juanTendraUnGuardarropa() {
		assertEquals(juan.getGuardarropas().size(), 1);
	}
	
	@Test (expected = YaSeEncuentraCargadaException.class)
	public void noSeEncuentraPermitidoIngresarUnaMismaPrendaAUnArmario()  {
		juan.cargarPrenda(armario,camisaLarga);
		juan.cargarPrenda(armario,camisaLarga);
	}
	
	@Test
	public void juanDebeTenerCincoPrendas() {
		assertEquals(armario.cantidadDePrendasGuardadas(), 5);
	}
	
	@Test
	public void siSeAgregaUnJeanASusPrendasHabraSeis(){
		armario.cargarPrenda(jean);
		assertEquals(armario.cantidadDePrendasGuardadas(), 6);
	}
	
	@Test
	public void siJuanCargaUnJeanASuArmarioDeberiaTenerOchoAtuendos() {
		juan.cargarPrenda(armario, jean);
		assertEquals(armario.pedirAtuendosSegun(APIDeMentiritas,juan).size(), 8);	
	}
	
	@Test
	public void losAtuendosTienenUnoDeCadaTipo() {
	juan.cargarPrenda(armario, jean);
	assertTrue(armario
		.pedirAtuendosSegun(APIDeMentiritas,juan)
		.stream()
		.allMatch(atuendo->this.atuendoTieneCategoria(atuendo, Categoria.Superior) 
				&& this.atuendoTieneCategoria(atuendo, Categoria.Inferior)  
				&&this.atuendoTieneCategoria(atuendo, Categoria.Calzado)));
	}
	
	public boolean atuendoTieneCategoria(Set<Prenda> unAtuendo, Categoria unaCategoria){
		return unAtuendo
				.stream()
				.anyMatch(prenda->this.prendaDeCategoria(prenda,unaCategoria));
	}
	
	public boolean prendaDeCategoria(Prenda unaPrenda,Categoria categoria) {
		return unaPrenda.getTipo().categoria == categoria;
	}
	
	@Test (expected = NoHayAtuendosDisponiblesException.class)
	public void siLaraPideUnAtuendoPeroNoTienePrendasSuficientesLanzaExcepcion(){
		Usuario lara = new Usuario(TipoUsuario.PREMIUM,0,"juan","123");
		Sugeridor.getInstance().sugerirPrendasPara(lara);
	}
	
	@Test(expected = MaximoDePrendasAlcanzadoException.class)
	public void siSeIntentaCargarMasCantidadDePrendasDeLaPermitidaLanzaException(){
		Usuario lara = new Usuario(TipoUsuario.GRATUITO,1,"juan","123");
		lara.agregarGuardarropa(otroArmario);
		lara.cargarPrenda(otroArmario, camisaCorta);
		lara.cargarPrenda(otroArmario, zapatos);
	}
	
	@Test 
	public void siUnUsuarioGratuitoNoSePasaDeLaCantidadPermitidaPuedeCargarPrenda(){
		Usuario lara = new Usuario(TipoUsuario.GRATUITO,1,"juan","123");
		lara.agregarGuardarropa(otroArmario);
		lara.cargarPrenda(otroArmario, camisaCorta);
		assertTrue(otroArmario.getPrendas().size() == 1);
	}
	
	@Test
	public void siUnUsuarioClasificaUnaSugerenciaYLuegoLoDeshaceQuedaPendiente() {
		Set<Prenda> atuendo = new HashSet<Prenda>();
		atuendo.add(jean);
		atuendo.add(camisaCorta);
		atuendo.add(gorra);
		atuendo.add(zapatos);
		Sugerencia sugerencia = new Sugerencia(atuendo,eventoConFrecuenciaUnica);
		juan.agregarSugerencia(sugerencia);
		juan.clasificarUnaSugerencia(sugerencia, TipoSugerencias.ACEPTADA);
		juan.deshacerUltimaOperacionDeSugerencia();
		assertEquals(sugerencia.getEstado(),TipoSugerencias.PENDIENTE);
	}
	
	@Test
	public void dosUsuariosCompartenUnGuardarropa() {
		Usuario lara = new Usuario(TipoUsuario.PREMIUM,0,"juan","123");
		lara.agregarGuardarropa(armario);
		assertEquals(lara.getGuardarropas(), juan.getGuardarropas());
	}
	
	public Set<Set<Prenda>> sugerirMasAceptarTodasLasSugerencias(Usuario usuario, Evento evento) {
		evento.sugerir(usuario);
		usuario.getSugerencias().stream().forEach(sugerencia -> usuario.clasificarUnaSugerencia(sugerencia, TipoSugerencias.ACEPTADA));
		return usuario.getSugerencias().stream().map(sugerencia -> sugerencia.getAtuendo()).collect(Collectors.toSet());
	}
	 
	@Test
	public void dosUsuariosCompartenUnGuardarropaYTienenSuficienteRopaParaHacerLosAtuendos() {
		juan.cargarPrenda(armario, jean);
		Set<Set<Prenda>> atuendosDeJuan = this.sugerirMasAceptarTodasLasSugerencias(juan, eventoConFrecuenciaUnica);
		
		Usuario lara = new Usuario(TipoUsuario.PREMIUM,100,"juan","123");
		
		lara.agregarGuardarropa(armario);
		lara.cargarPrenda(armario, pantalon);
		lara.cargarPrenda(armario, camisaDeLara);
		lara.cargarPrenda(armario, sombrero);
		lara.cargarPrenda(armario, zapatillas);

		eventoConFrecuenciaUnica.sugerir(lara);
		Set<Set<Prenda>> atuendosDeLara = lara.getSugerencias().stream().map(sugerencia -> sugerencia.getAtuendo()).collect(Collectors.toSet());

		assertFalse(atuendosDeLara.containsAll(atuendosDeJuan));
	}
	
	@Test (expected = domain.exceptions.NoHayAtuendosDisponiblesException.class)
	public void unoDeLosUsuariosConGuardarropaCompartidoNoPuedeEjecutarSusAtuendosPorFaltaDeRopa() {
		Sugeridor.getInstance().setProveedorDeClima(APIDeMentiritas);

		juan.cargarPrenda(armario, jean);
		Usuario lara = new Usuario(TipoUsuario.PREMIUM,0,"juan","123");
		lara.agregarGuardarropa(armario);
		
		this.sugerirMasAceptarTodasLasSugerencias(juan, eventoConFrecuenciaUnica);
		
		this.sugerirMasAceptarTodasLasSugerencias(lara, eventoConFrecuenciaUnica);
		
	}
	
	@Test
	public void siNoSeAceptanLasSugerenciasConGuardarropaCompartidoSeGeneranLasMismasSugerencias() {
		juan.cargarPrenda(armario, jean);
		Usuario lara = new Usuario(TipoUsuario.PREMIUM,0,"juan","123");
		lara.agregarGuardarropa(armario);
		
		eventoConFrecuenciaUnica.sugerir(juan);
		Set<Set<Prenda>> atuendosDeJuan = juan.getSugerencias().stream().map(sugerencia -> sugerencia.getAtuendo()).collect(Collectors.toSet());
		eventoConFrecuenciaUnica.sugerir(lara);
		Set<Set<Prenda>> atuendosDeLara = lara.getSugerencias().stream().map(sugerencia -> sugerencia.getAtuendo()).collect(Collectors.toSet());
		
		assertTrue(atuendosDeJuan.equals(atuendosDeLara));
		
	}
	
	public boolean todaLaRopaEstaLimpia(Set<Set<Prenda>> atuendos) {
		return atuendos.stream().allMatch(atuendo -> atuendo.stream().allMatch(prenda -> !prenda.isUsada()));
	}
	
	@Test
	public void laRopaEstaSuciaAlAceptarSugerencias() {
		juan.cargarPrenda(armario, jean);
		Set<Set<Prenda>> atuendosDeJuan = this.sugerirMasAceptarTodasLasSugerencias(juan, eventoConFrecuenciaUnica);
		assertFalse(this.todaLaRopaEstaLimpia(atuendosDeJuan));
	}
	
	@Test
	public void lavarLaRopaLuegoDeUsarla() {
		juan.cargarPrenda(armario, jean);
		Set<Set<Prenda>> atuendosDeJuan = this.sugerirMasAceptarTodasLasSugerencias(juan, eventoConFrecuenciaUnica);
		juan.lavarLaRopa();
		assertTrue(this.todaLaRopaEstaLimpia(atuendosDeJuan));
	}
	
	@Test
	public void lavoRopaYVuelvoAGenerarSugerencias(){
		juan.cargarPrenda(armario, jean);
		Set<Set<Prenda>> atuendosDeJuan = this.sugerirMasAceptarTodasLasSugerencias(juan, eventoConFrecuenciaUnica);
		juan.lavarLaRopa();
		
		Usuario lara = new Usuario(TipoUsuario.PREMIUM,0,"juan","123");
		lara.agregarGuardarropa(armario);
		Set<Set<Prenda>> atuendosDeLara = this.sugerirMasAceptarTodasLasSugerencias(lara, eventoConFrecuenciaUnica);
		
		assertEquals(atuendosDeJuan, atuendosDeLara);
	}
	
}
