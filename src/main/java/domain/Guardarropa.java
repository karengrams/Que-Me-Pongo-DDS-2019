package domain;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import com.google.common.collect.*;
import domain.apisClima.*;
import domain.enums.*;

@Entity
public class Guardarropa extends SuperClase{

	// ---------------------------- Atributos -------------------------------
	
	@OneToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
	@JoinColumn(name="id_Guardarropa") 
	public Set<Prenda> prendas;

	public String nombre;
	
	// ------------------ Getters, setters y constructores ------------------
	public Guardarropa() {
		prendas = new HashSet<Prenda>();
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String otro) {
		nombre = otro;
	}
	public void cargarPrenda(Prenda unaPrenda){
		prendas.add(unaPrenda);
	}
	
	public Set<Prenda> getPrendas() {
		return prendas;
	}
	public void borrarPrendas() {
		this.prendas = new HashSet<Prenda>();
	}
	
	// ------------------------------ Metodos -------------------------------
	
	public List<Set<Prenda>> pedirAtuendosSegun(ProveedorClima proveedor,Usuario unUser){
		Set<Set<Prenda>> elAux = new HashSet<Set<Prenda>>();
		elAux = this.parteNoSuperior(proveedor,unUser); 
		ArrayList<Set<Prenda>> atuendosInferior = new ArrayList<Set<Prenda>>(elAux);
		Set<Set<Prenda>> atuendoSup = parteSuperior(proveedor,unUser);
		ArrayList<Set<Prenda>> listaAtuSuperior = new ArrayList<Set<Prenda>>(atuendoSup);
		ArrayList<Set<Prenda>> listaReturn = new ArrayList<Set<Prenda>>();
		for (int i=0;i<atuendosInferior.size();i++)
		{
			for(int j=0;j<listaAtuSuperior.size();j++)
			{
				Set<Prenda> atuInf = new HashSet<Prenda>(atuendosInferior.get(i));
				Set<Prenda> atuSup = new HashSet<Prenda>(listaAtuSuperior.get(j));
				atuInf.addAll(atuSup);
				listaReturn.add(atuInf);
			}
		}
		 return listaReturn;
	}
	
	private Set<Prenda> noSuperior(){
		return this.prendasNoUsadas().stream().filter(p->p.getTipo().categoria != 
				Categoria.Superior).collect(Collectors.toSet());
	}

	private Set<Set<Prenda>> parteNoSuperior(ProveedorClima clima,Usuario user){
		Set<Prenda> partesInferiores = this.noSuperior();
		Set<Set<Prenda>> powerSetInferiores = Sets.powerSet(partesInferiores); 
		powerSetInferiores = powerSetInferiores.stream()
				.filter(at->this.parteInferiorValida(at, clima,user))
				.collect(Collectors.toSet());
		return powerSetInferiores;
	}
	
	private Set<Set<Prenda>> parteSuperior(ProveedorClima clima,Usuario unUsuario){
		Set<Prenda> partesSuperiores = this.soloSuperior();
		Set<Set<Prenda>> powerSetSuperiores = Sets.powerSet(partesSuperiores); 
		powerSetSuperiores = powerSetSuperiores.stream()
				.filter(at->this.parteSuperiorValida(at,clima,unUsuario))
				.collect(Collectors.toSet());
		return powerSetSuperiores;
		
	}
	
	private Set<Prenda> soloSuperior(){
		Set<Prenda> ret = this.prendasNoUsadas().stream().filter(p->p.getTipo().categoria == 
				Categoria.Superior).collect(Collectors.toSet());
		return ret;
	}
	
	private boolean parteSuperiorValida(Set<Prenda> ps, ProveedorClima clima,Usuario unUser) {
		Set<Prenda> aux = ps.stream().filter(prenda->prenda.getEsBase()).collect(Collectors.toSet());
		boolean soloUnaPrendaBase = aux.size() == 1;
		int aux2 = ps.stream().mapToInt(prenda -> prenda.getNivelAbrigo()).sum();
		boolean abrigaBien = this.EstaEnRango(aux2,clima,unUser,Categoria.Superior);
		boolean soloUnaCampera = ps.stream().filter(prenda->prenda.getTipo() == TipoPrenda.Campera).collect(Collectors.toSet()).size()<2;
		boolean soloUnTapado = ps.stream().filter(prenda->prenda.getTipo() == TipoPrenda.Tapado).collect(Collectors.toSet()).size()<2;
		boolean soloUnBuzo= ps.stream().filter(prenda->prenda.getTipo() == TipoPrenda.Buzo).collect(Collectors.toSet()).size()<2;

		return soloUnaPrendaBase && abrigaBien
				&& soloUnaCampera && soloUnTapado && soloUnBuzo;
	}

	public boolean EstaEnRango(int nivelAbrigo,ProveedorClima clima,Usuario unUsuario,Categoria cat) {
		int nf =this.nivelFrio(clima.temperatura());
		nf += calificacionUsuario(unUsuario,cat);
		return nivelAbrigo >= nf-1 && nivelAbrigo <= nf+2;
	}
	
	private boolean parteInferiorValida(Set<Prenda> ps,ProveedorClima clima,Usuario user) {
		return this.contienePrendasDeCategoria(ps, Categoria.Inferior)
				&& this.contienePrendasDeCategoria(ps, Categoria.Calzado)
				&& this.abrigaCorrectamenteInferior(ps,clima,user);
	}
	
	private boolean abrigaCorrectamenteInferior(Set<Prenda> prenInf,ProveedorClima clima,Usuario user) {
		boolean abriganBien = prenInf.stream().
				filter(pr->EstaEnRango(pr.getNivelAbrigo(),clima,user,pr.getTipo().categoria)).
				collect(Collectors.toSet()).size()>1;
		return abriganBien;
	}
	
	private boolean contienePrendasDeCategoria(Set<Prenda> atuendo, Categoria unaCategoria) {
		Set<Prenda> aux;
		if (unaCategoria != Categoria.Inferior)
			return atuendo.stream().anyMatch(prenda->prenda.getTipo().categoria == unaCategoria) 
					&& atuendo.stream().filter(t->t.getTipo().categoria == unaCategoria).
					collect(Collectors.toList()).size() == 1; 
		
		aux = atuendo.stream().filter(prenda->prenda.getTipo().
				categoria == unaCategoria).collect(Collectors.toSet());
		return aux.size() == 1;
	}

	private int nivelFrio(double temperatura) {
		if (temperatura < 1)
			return 5;
		if (temperatura < 9)
			return 4;
		if (temperatura <= 13)
			return 3;
		if (temperatura < 19)
			return 2;
		if (temperatura < 23)
			return 1; 
		else
			return -1;
	}
	
	private int calificacionUsuario(Usuario user,Categoria cat)
	{
		List<Calificacion> califUser = user.getCalificaciones();
		List<TipoSensaciones> sensaciones = califUser.stream().
				filter(calif->calif.getCategoria() == cat).
				map(calif->calif.getSensacion()).collect(Collectors.toList());
		int nivelFriolento = sensaciones.stream().filter(sensa->sensa == TipoSensaciones.FRIO)
		.collect(Collectors.toList()).size();
		int nivelCaluroso = sensaciones.stream().filter(sensa->sensa == TipoSensaciones.CALOR)
				.collect(Collectors.toList()).size();
		return nivelFriolento - nivelCaluroso;
	}
	
	public Set<Prenda> prendasNoUsadas(){
		return prendas.stream().filter(prenda -> !prenda.isUsada()).collect(Collectors.toSet());
	}
	
	public int cantidadDePrendasGuardadas() {
		return this.prendas.size();
	}
	public List<String> listPrendas(){
		return this.getPrendas().stream().map(p->p.prenda()).collect(Collectors.toList());
	}
}
