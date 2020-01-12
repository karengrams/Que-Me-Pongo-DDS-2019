package tiempo.checkboxes;

import java.util.HashMap;

import domain.frecuenciasDeEventos.FrecuenciaAnual;
import domain.frecuenciasDeEventos.FrecuenciaDeEvento;
import domain.frecuenciasDeEventos.FrecuenciaUnicaVez;
import spark.Request;

public class UnicaVezCheckbox implements Tiempo{
	public Integer mes;
	public Integer dia;
	public Integer anio;
	
	public FrecuenciaDeEvento obtenerFrecuencia() {
		return new FrecuenciaUnicaVez(anio, mes, dia);
	}
	
	public boolean verificarTiempo(String tiempo) {
		return tiempo.equals("UnicaVez");
	}
	
	public String esPeriodico() {
		return "esUnicaVez";
	}
	
	public boolean datosIngresadosCorrectamente(Request req) {
		String mesString = req.queryParams("mes");
		String diaString = req.queryParams("dia");
		String anioString = req.queryParams("anio");
		mes = Integer.parseInt(mesString);
		dia = Integer.parseInt(diaString);
		anio = Integer.parseInt(anioString);
		return validarFecha();
	}

	public boolean validarFecha() {
		return validarMes() && validarDias() && validarAnio();
	}
	
	private boolean validarAnio() {
		return anio >= 2019;
	}

	private boolean validarDias() {
		return dia>=1 && dia<=30;
	}

	private boolean validarMes() {
		return mes >=1 && mes <=12;
	}
}
