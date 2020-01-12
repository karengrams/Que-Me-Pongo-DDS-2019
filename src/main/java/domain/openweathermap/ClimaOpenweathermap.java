package domain.openweathermap;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

public class ClimaOpenweathermap<E> {
	
	@SerializedName("main")
	@Expose
	private LinkedTreeMap<String, Double> main;
	public double getTemperatura(){
		return main.get("temp");
	}

	@SerializedName("wind")
	@Expose
	private LinkedTreeMap<String,Double> wind;
	public double getWind(){
		return wind.get("speed");
	}
	
	@SerializedName("weather")
	@Expose
	private List weather;	
	public double getID(){
		return ((LinkedTreeMap<String, Double>) weather.get(0)).get("id");
	}
}