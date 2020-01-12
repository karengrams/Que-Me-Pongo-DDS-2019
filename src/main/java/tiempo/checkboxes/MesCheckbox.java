package tiempo.checkboxes;

import java.util.HashMap;

import domain.frecuenciasDeEventos.FrecuenciaDeEvento;
import domain.frecuenciasDeEventos.FrecuenciaDiaria;
import domain.frecuenciasDeEventos.FrecuenciaMensual;
import spark.Request;

public class MesCheckbox implements Tiempo{
	public Integer dia;
	
	public FrecuenciaDeEvento obtenerFrecuencia() {
		return new FrecuenciaMensual(dia);
	}
	
	public boolean verificarTiempo(String tiempo) {
		return tiempo.equals("Mensual");
	}
	
	public String esPeriodico() {
		return "esMensual";
	}
	

	public boolean datosIngresadosCorrectamente(Request req) {
		String diaString = req.queryParams("dia");
		dia = Integer.parseInt(diaString);
		return validarFecha();
	}

	public boolean validarFecha() {
		return validarDia();
	}
	
	private boolean validarDia() {
		return dia >=1 && dia <=30;
	}
}
