package domain.apisClima;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import domain.openweathermap.*;
import domain.exceptions.*;

public class OpenWeatherMapAPI implements ProveedorClima{	

	private int COUNTRY_ID = 3433955;
	private String APP_ID = "840f5cd8488cbd8d00decbd2bb8cd6a0";

	Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitUsersService service = retrofit.create(RetrofitUsersService.class);
    Call<ClimaOpenweathermap> call ;
    
    public double temperatura() {
    	try{
    		call=service.getTemperatura(APP_ID, COUNTRY_ID);
        	Response<ClimaOpenweathermap> response = call.execute();
        	ClimaOpenweathermap user = response.body();
        	return user.getTemperatura();

    	}
    	catch (Exception ex){
    		throw new ErrorConAPIException(ex.getMessage());
    	}
    }
    
    public double ID() {
    	try{
    		call=service.getID(APP_ID, COUNTRY_ID);
        	Response<ClimaOpenweathermap> response = call.execute();
        	ClimaOpenweathermap user = response.body();
        	return user.getID();

    	}
    	catch (Exception ex){
    		throw new ErrorConAPIException(ex.getMessage());
    	}
    }
    
    public boolean lluviasFuertes() {
    	return this.ID()>=502 && this.ID()<=504 && this.ID()==202;
    }
        

    
}
