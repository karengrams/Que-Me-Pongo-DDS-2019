package tiempo.checkboxes;

import java.util.HashMap;

import domain.frecuenciasDeEventos.FrecuenciaDeEvento;
import spark.Request;

public interface Tiempo {
	public String esPeriodico();
	public boolean verificarTiempo(String tiempo);
	public boolean validarFecha();
	public boolean datosIngresadosCorrectamente(Request req);
	public FrecuenciaDeEvento obtenerFrecuencia();
}
