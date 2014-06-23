package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model;

import java.util.ArrayList;
import java.util.List;

public class EventoCollection {
	

	private List<Evento> eventos;
	

	public EventoCollection() {
		super();
		eventos = new ArrayList<Evento>();
	}
	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}
	public void addEvento(Evento evento) {
		eventos.add(evento);
	}
	
	
	
	
	
	
	

}
