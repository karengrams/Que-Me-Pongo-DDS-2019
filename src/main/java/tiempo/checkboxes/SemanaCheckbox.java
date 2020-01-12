package tiempo.checkboxes;

import java.util.HashMap;

import domain.frecuenciasDeEventos.FrecuenciaDeEvento;
import domain.frecuenciasDeEventos.FrecuenciaMensual;
import domain.frecuenciasDeEventos.FrecuenciaSemanal;
import spark.Request;

public class SemanaCheckbox implements Tiempo{
	public Integer dia ;
	
	public FrecuenciaDeEvento obtenerFrecuencia() {
		return new FrecuenciaSemanal(dia);
	}
	
	public boolean verificarTiempo(String tiempo) {
		return tiempo.equals("Semanal");
	}
	
	public String esPeriodico() {
		return "esSemanal";
	}
	
	public boolean datosIngresadosCorrectamente(Request req) {
		String diaString = req.queryParams("dia");
		dia = Integer.parseInt(diaString);
		return validarFecha();
	}
	
	public boolean validarFecha() {
		return validarDiaDeSemana();
	}

	private boolean validarDiaDeSemana() {
		return dia >=1 && dia <=7;
	}
}
