package domain;
import java.util.Set;
import java.util.stream.Collectors;
import domain.apisClima.ProveedorClima;
import domain.exceptions.*;

public class Sugeridor {
	private ProveedorClima proveedorDeClima;

	private static class SugeridorHolder {
		private static final Sugeridor INSTANCE = new Sugeridor();
	}

	public static Sugeridor getInstance() {
		return SugeridorHolder.INSTANCE;
	}

	public void setProveedorDeClima(ProveedorClima proveedorDeClima) {
		this.proveedorDeClima = proveedorDeClima;
	}

	public ProveedorClima getProveedorDeClima() {
		return proveedorDeClima;
	}

	public Set<Set<Prenda>> sugerirPrendasPara(Usuario unUsuario) {
		if(this.proveedorDeClima == null) {
			throw new DebeHaberUnProveedorDeClimaAsignado("WARNING: debe asignarle un proveedor de clima para poder obtener sugerencias.");
		}
		else {
		Set<Set<Prenda>> atuendos = unUsuario.getGuardarropas().stream()
				.map(guardarrropa -> guardarrropa.pedirAtuendosSegun(proveedorDeClima, unUsuario))
				.flatMap(guardarropa -> guardarropa.stream()).collect(Collectors.toSet());
		if (atuendos.isEmpty())
			throw new NoHayAtuendosDisponiblesException(
					"WARNING: falta ingresar prendas en los guardarropas para obtener posibles atuendos");

			return atuendos;
		}
	}

}
