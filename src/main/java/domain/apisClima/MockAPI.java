package domain.apisClima;
public class MockAPI implements ProveedorClima{	
	private double temperatura;
	private double velocidadViento;
	private boolean lluviasFuertes;
	
	public MockAPI(double temp,double vel,boolean lluvias){
		temperatura=temp;
		velocidadViento=vel;
		lluviasFuertes=lluvias;
	}
	
	//Setters
	public void velocidadViento(double velocidadViento) {
		this.velocidadViento = velocidadViento;
	}
	
	public void temperatura(double temp) {
		this.temperatura=temp;
	}
	
	public void setLluviasFuertes(boolean lluviasFuertes) {
		this.lluviasFuertes = lluviasFuertes;
	}
	
	//Getters
	public boolean lluviasFuertes() {return lluviasFuertes;}
	
	public double velocidadViento() {return velocidadViento;}
	
	public double temperatura() {return temperatura;}
	
}
