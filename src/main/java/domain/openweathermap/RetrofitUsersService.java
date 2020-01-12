package domain.openweathermap;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitUsersService {
	
    @GET("weather")
    Call<ClimaOpenweathermap> getTemperatura(
    		@Query("appid") String appid,
    		@Query("id") int id);
   
    @GET("weather")
    Call<ClimaOpenweathermap> getWind(
    		@Query("appid") String appid,
    		@Query("id") int id);
    
    @GET("weather")
    Call<ClimaOpenweathermap> getID(
    		@Query("appid") String appid,
    		@Query("id") int id);
    		
}
