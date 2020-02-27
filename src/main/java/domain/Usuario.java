package domain;

import java.util.*;
import java.time.*;
import domain.enums.*;
import domain.exceptions.*;
import java.util.stream.*;
import domain.SuperClase;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;


import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity 
public class Usuario extends SuperClase{
	// ---------------------------- Atributos -------------------------------
	@Column(unique=true)
	private String nombreUsuario;
	
	private String password;
	
	@Enumerated
	private TipoUsuario tipo;
	
	private int maximoDePrendas;
	
	@ManyToMany (cascade = CascadeType.PERSIST,fetch=FetchType.EAGER)
	private Set<Guardarropa> guardarropas = new HashSet<Guardarropa>();
	
	@OneToMany (cascade = CascadeType.PERSIST)
	@JoinColumn(name="id_usuario")
	@OrderColumn
	private List<Sugerencia> sugerencias = new ArrayList<Sugerencia>();
	
	private Sugerencia ultimaSugerencia() {
		return sugerencias.get(sugerencias.size()-1);//obtiene el ultimo.
	}
	
	@ManyToMany (cascade = CascadeType.PERSIST)
	private Set<MedioDeNotificacion> medios = new HashSet<MedioDeNotificacion>();
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	private Set<Evento> eventos = new HashSet<Evento>();
	
	@OneToMany (cascade = CascadeType.PERSIST) 
	@JoinColumn(name="id_Usuario") 
	private List<Calificacion> calificaciones = new ArrayList<Calificacion>();
	
	// ------------------ Getters, setters y constructores ------------------
	
	
	public Usuario() {}
	
	public Usuario(TipoUsuario tipo, int maximoDePrendas,String nombre,String pass) {
		this.tipo = tipo;
		this.maximoDePrendas = maximoDePrendas;
		this.nombreUsuario = nombre;
		this.password = pass;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public Set<Evento> eventosProximos(LocalDateTime fecha) {
		
		return this.eventos().stream().filter(evento->evento.esProximo(fecha)&& !this.tengoSugerenciaDeEsteEvento(evento)).collect(Collectors.toSet());
	}

	public List<Sugerencia> getSugerencias() {
		return sugerencias;
	}
	public int getMaximoPrendas() {
		return this.maximoDePrendas;
	}
	public Set<Guardarropa> getGuardarropas() {
		return guardarropas;
	}
	
	public List<Calificacion> getCalificaciones(){
		return this.calificaciones;
	}
	
	public TipoUsuario getTipo() {
		return tipo;
	}
	
	public Set<Evento> eventos() { 
		return eventos;
		}
	
	public Set<Evento> eventosSugeridos(){
		return this.eventos().stream().filter(evento->this.tengoSugerenciaDeEsteEvento(evento) && !evento.yaSucedio()).collect(Collectors.toSet());
	}
	
	public Set<Set<Prenda>> atuendosSugeridosDe(Evento unEvento){
		return this.sugerencias.stream()
							   .filter(sugerencia->sugerencia.mismoEvento(unEvento))
							   .map(sugerencia->sugerencia.getAtuendo()).collect(Collectors.toSet());
	}
	
	private Set<Sugerencia> getSugerenciasConEventosFinalizados(){
		return sugerencias.stream().filter(sugerencia-> sugerencia.getEvento().yaSucedio()).collect(Collectors.toSet());
	}
	
	public Set<Evento> eventosConCambioDeClima(){
		return this.eventos.stream().filter(evento->this.atuendosNoSeEncuentranAptosPara(evento)).collect(Collectors.toSet());
	}
	
	public int prendasCreadas(){
		return this.guardarropas.stream().flatMap(g->g.getPrendas().stream()).collect(Collectors.toList()).size();
	}
	
	public void setMaximoDePrendas(int maximoDePrendas) {
		this.maximoDePrendas = maximoDePrendas;
	}
	
	// ------------------------------ Metodos -------------------------------

	

	public void validacionSegunTipoUsuario(int cantidadDePrendasDelGuardarropas) {
		if(tipo == TipoUsuario.GRATUITO && cantidadDePrendasDelGuardarropas >= maximoDePrendas) {
			throw new SeExcedioElLimiteDeCapacidadDelGuardarropaException("El guardarropa ya contiene el limite de su capacidad.");
		}
	}
	
	public void calificar(Categoria parteCuerpo,TipoSensaciones sensacion) {
		this.calificaciones.add(new Calificacion(parteCuerpo,sensacion));
	}

	public boolean tengoSugerenciaDeEsteEvento(Evento evento) {
		return sugerencias.stream().anyMatch(sugerencia->sugerencia.getEvento().equals(evento));
	}
	
	public void agregarGuardarropa(Guardarropa unGuardarropa) {
//		this.validacionSegunTipoUsuario(unGuardarropa.prendas().size()-1); 
		this.guardarropas.add(unGuardarropa);
	}
	
	public void cargarPrenda(Guardarropa unGuardarropa,Prenda unaPrenda) {
		if(this.maximoDePrendas<=this.prendasCreadas()){
			throw new MaximoDePrendasAlcanzadoException("No se puede cargar la prenda dado que se ha alcanzado el maximo permitido.");
		}
		if(this.yaSeCargoLaPrenda(unaPrenda)) {
			throw new YaSeEncuentraCargadaException("La prenda ingresada ya se encuentra cargada.");
		}
		if(unGuardarropa==null || unaPrenda ==null) {
			throw new TieneParametrosNulosException("La creacion de la prenda requiere parametros no nulos.");
		}
	
		this.validacionSegunTipoUsuario(unGuardarropa.getPrendas().size());		
		unGuardarropa.cargarPrenda(unaPrenda);
	}

	public boolean yaSeCargoLaPrenda(Prenda unaPrenda) {
		return guardarropas.stream().anyMatch(guardarropa->guardarropa.getPrendas().contains(unaPrenda));
	}
		
	public void agregarSugerencia(Sugerencia sugerenciaNueva){
		sugerencias.add(sugerenciaNueva);
	}
	
	public void clasificarUnaSugerencia(Sugerencia sugerencia, TipoSugerencias tipo) {
		if (!sugerencias.contains(sugerencia)){
			throw new NoPoseeLaSugerenciaException("No posee la sugerencia que se esta intentando clasificar.");
		}
		if(tipo.equals(TipoSugerencias.ACEPTADA)) {
			sugerencia.getAtuendo().forEach(prenda -> prenda.setUsada(true));
		}
			sugerencia.setEstado(tipo);
	}
	
	public boolean atuendosNoSeEncuentranAptosPara(Evento unEvento) {
		Set<Set<Prenda>> atuendosDelEvento = this.atuendosSugeridosDe(unEvento);
		return atuendosDelEvento.stream().allMatch(atuendo->!unEvento.obtenerAtuendos(this).contains(atuendo));
	}
	
	public void deshacerUltimaOperacionDeSugerencia() {
		if (this.ultimaSugerencia() != null) {
			this.ultimaSugerencia().getAtuendo().forEach(prenda -> prenda.setUsada(false));
			this.ultimaSugerencia().setEstado(TipoSugerencias.PENDIENTE);
		}
	}
	
	public void agendarEvento(Evento unEvento) {
		this.eventos.add(unEvento);
	}
	
	public void desagendarEvento(Evento unEvento) {
		this.eventos = eventos.stream().filter(evento -> !evento.equals(unEvento)).collect(Collectors.toSet());
	}
	
	public void lavarLaRopa() {
		Set<Sugerencia> sugerenciasConEventosFinalizados = this.getSugerenciasConEventosFinalizados();
		sugerenciasConEventosFinalizados.forEach(sugerencia -> sugerencia.setPrendasComoNoUsadas());
	}
	
	public void notificarSugerenciasNuevas() {
		this.medios.stream().forEach(medio->medio.notificarNuevasSugerencias(this));
	}
	
	public void notificarAlertaMeterologicaDe(Evento unEvento) {
		System.out.println("Usuario "+this+" afectado por cambio de temperatura.");
		this.medios.stream().forEach(medio->medio.notificarAlertaMeterologica(unEvento,this));
	}

	public void validarContrasenia(String contra) {
		if (!contra.equals(this.password))
			throw new NoPoseeLaSugerenciaException("¡Contraseña incorrecta!");
	}
	
	public Guardarropa buscarGuardarropa(int id) {
		try {
			return this.getGuardarropas().stream().filter(g->g.getId()==id).collect(Collectors.toList()).get(0);
		}catch(Exception e) {
			throw new TieneParametrosNulosException("Se debe ingresar un guardarropas correcto.");
		}
	}
	
}