package domain.climaMetaWeather;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"the_temp",
		"wind_speed"
	})

public class ClimaGeneral {
	@JsonProperty("the_temp")
	private Double theTemp;
	
	@JsonProperty("the_temp")
	public Double getTheTemp() {
	return theTemp;
	}
	
	@JsonProperty("the_temp")
	public void setTheTemp(Double theTemp) {
	this.theTemp = theTemp;
	}
	
	@JsonProperty("wind_speed")
	private Double theWind;
	
	@JsonProperty("wind_speed")
	public Double getTheWind() {
		return theWind*1.60934;
	}
	
	@JsonProperty("wind_speed")
	public void setTheWind(Double theWind) {
		this.theWind=theWind;
	}
	
	@JsonProperty("weather_state_abbr")
	private String state;
	
	@JsonProperty("weather_state_abbr")
	public String getTheState(){
		return state;
	}
	
	@JsonProperty("weather_state_abbr")
	public void setTheState(String state) {
		this.state=state;
	}
	
}
