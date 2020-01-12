package domain;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.time.*;

public class JobsUsuarios implements Runnable{
	LocalDateTime fechaTest; 
	@Override
	public void run() {
		LocalDateTime fechaActual = LocalDateTime.now();
	//	RepositorioDeUsuarios.getInstance().notificarAUsuariosAfectadosPorCambioDeClima();
		RepositorioDeUsuarios.getInstance().sugerir(fechaActual);
	//	RepositorioDeUsuarios.getInstance().lavarTodaLaRopaSucia();
	}
	public static void main() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        Runnable codigoAEjecutar = new JobsUsuarios();
        int retrasoInicial = 0;
        int periodo = 1;//Podria ser 15 para que ejecute cada 15 minutos.
        scheduler.scheduleAtFixedRate(codigoAEjecutar, retrasoInicial, periodo, TimeUnit.MINUTES);//cambiar a TimeUnit.SECONDS para testear
    }
	public void runTest() {
		RepositorioDeUsuarios.getInstance().sugerir(fechaTest);
	}
	public LocalDateTime getFechaTest() {
		return fechaTest;
	}
	public void setFechaTest(LocalDateTime fechaTest) {
		this.fechaTest = fechaTest;
	}
	
}
