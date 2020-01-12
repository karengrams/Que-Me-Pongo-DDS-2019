import org.junit.Before;
import domain.apisClima.OpenWeatherMapAPI;

public class OpenWeatherMapAPITest {

	OpenWeatherMapAPI weatherAPI;
	
	@Before
	public void setUp() throws Exception {
		weatherAPI = new OpenWeatherMapAPI();
	}


}
