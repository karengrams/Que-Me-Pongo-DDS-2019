package tiempo.checkboxes;

import java.util.HashMap;

import domain.frecuenciasDeEventos.FrecuenciaAnual;
import domain.frecuenciasDeEventos.FrecuenciaDeEvento;
import domain.frecuenciasDeEventos.FrecuenciaDiaria;
import spark.Request;

public class AnioCheckbox implements Tiempo{
	public Integer mes;
	public Integer dia;
	
	public FrecuenciaDeEvento obtenerFrecuencia() {
		return new FrecuenciaAnual(mes, dia);
	}
	
	public boolean verificarTiempo(String tiempo) {
		return tiempo.equals("Anual");
	}
	
	public String esPeriodico() {
		return "esAnual";
	}

	public boolean datosIngresadosCorrectamente(Request req) {
		String mesString = req.queryParams("mes");
		String diaString = req.queryParams("dia");
		mes = Integer.parseInt(mesString);
		dia = Integer.parseInt(diaString);
		return validarFecha();
	}

	public boolean validarFecha() {
		return validarMes() && validarDias();
	}


	private boolean validarDias() {
		return dia>=1 && dia<=30;
	}

	private boolean validarMes() {
		return mes >=1 && mes <=12;
	}
}
