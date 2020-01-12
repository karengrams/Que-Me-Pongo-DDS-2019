package domain.climaMetaWeather;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"consolidated_weather"
})

public class ClimaMetaWeather {
	@JsonProperty("consolidated_weather")
	private List<ClimaGeneral> consolidatedWeather = null;
	
	@JsonProperty("consolidated_weather")
	public List<ClimaGeneral> getConsolidatedWeather() {
	return consolidatedWeather;
	}

	@JsonProperty("consolidated_weather")
	public void setConsolidatedWeather(List<ClimaGeneral> consolidatedWeather) {
	this.consolidatedWeather = consolidatedWeather;
	}
}
