package domain.apisClima;
import com.sun.jersey.api.client.ClientResponse;
import domain.climaMetaWeather.*;

public class MetaWeatherAPI implements ProveedorClima {
	RequestServiceMetaWeather requester = new RequestServiceMetaWeather();

	ClientResponse response = requester.getInfoAPI();

	String json = response.getEntity(String.class);

	JsonFactory jsonFactory = new JsonFactory();
	
	ClimaMetaWeather clima = jsonFactory.fromJson(json,ClimaMetaWeather.class);
	
	public double temperatura() {	
		return clima.getConsolidatedWeather().get(0).getTheTemp();
	}

	public String estado(){
		return clima.getConsolidatedWeather().get(2).getTheState();
	}
	
	public boolean lluviasFuertes() {
		return this.estado()=="hr";
	}
	
}
