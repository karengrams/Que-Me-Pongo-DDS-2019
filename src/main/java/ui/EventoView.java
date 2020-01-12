package ui;

import java.time.LocalDateTime;

import domain.QueMePongoModel;
//@Observable
public class EventoView {
	private QueMePongoModel model= new QueMePongoModel();
	private static class EventoViewHolder {
		private static final EventoView INSTANCE = new EventoView();
	}
	public static EventoView getInstance() {
		return EventoViewHolder.INSTANCE;
	}

	public void setQueMePongoModel(QueMePongoModel model) {
		this.model = model;
	}
	public LocalDateTime getFechaInicio() {
		return QueMePongoModel.fecha(model.getFechaInicio());
	}
}
