package ui;
import domain.*;
import org.uqbar.arena.layout.*;
import org.uqbar.arena.widgets.*;
import org.uqbar.arena.windows.*;
import org.uqbar.arena.widgets.tables.*;

public class QueMePongoView extends MainWindow<QueMePongoModel> {
	public QueMePongoView(){
		super(new QueMePongoModel());	  
	}
	
	  public void createContents(Panel mainPanel){		  
		  	this.setTitle("Que Me Pongo");
			new Label(mainPanel).setText("¡Bienvenido a Que Me Pongo!");
			new Label(mainPanel).setText("Ingrese dos fechas (AAAA,MM,DD) para buscar los eventos que se encuentran entre las mismsas");
			mainPanel.setLayout(new VerticalLayout());
			Panel panelHorizontal= new Panel(mainPanel).setLayout(new HorizontalLayout());
			new Label(panelHorizontal).setText("Ingrese una fecha inicial:");
			new NumericField(panelHorizontal).setWidth(100).bindValueToProperty("fechaInicio");
			new Label(panelHorizontal).setText("Ingrese otra fecha final:");
			new NumericField(panelHorizontal).setWidth(100).bindValueToProperty("fechaFin");
			new Button(mainPanel).setCaption("Obtener eventos").onClick(()->this.getModelObject().listarEventos());
			Table<Evento> tabla = new Table<Evento>(mainPanel, Evento.class);
			tabla.bindItemsToProperty("eventos");
			new Column<Evento>(tabla).setTitle("Fecha").setFixedSize(150).bindContentsToProperty("fecha");
			new Column<Evento>(tabla).setTitle("Frecuencia").setFixedSize(150).bindContentsToProperty("frecuencia");
			new Column<Evento>(tabla).setTitle("Descripcion").setFixedSize(300).bindContentsToProperty("descripcion");
			new Column<Evento>(tabla).setTitle("¿Sugerencias listas?").setFixedSize(100).bindContentsToProperty("sugerenciasListas");
			new Label(mainPanel).bindValueToProperty("messageError");
	  }
	  

	  
	  public static void main(String[] args) {
		    new QueMePongoView().startApplication();
	  }

	  
}