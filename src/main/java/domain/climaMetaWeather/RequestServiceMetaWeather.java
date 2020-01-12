package domain.climaMetaWeather;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RequestServiceMetaWeather {
	private Client client;
	private static final String API_META_WEATHER = "https://www.metaweather.com/api/";
    private static final String RESOURCE = "location/468739";
    
    //Inicializacion del cliente.
    public RequestServiceMetaWeather() {
        this.client = Client.create();
        //En la documentacion se puede ver como al cliente agregarle un ClientConfig
        //para agregarle filtros en las respuestas (por ejemplo, para loguear).
    }
    
  //Prueba de concepto de un parametro y los mensajes por separado para identificar los tipos de datos.
    public ClientResponse getInfoAPI() {
    	WebResource recurso = this.client.resource(API_META_WEATHER).path(RESOURCE);
    	WebResource.Builder builder = recurso.accept(MediaType.APPLICATION_JSON);
    	ClientResponse response = builder.get(ClientResponse.class);
        return response;
    }
}
