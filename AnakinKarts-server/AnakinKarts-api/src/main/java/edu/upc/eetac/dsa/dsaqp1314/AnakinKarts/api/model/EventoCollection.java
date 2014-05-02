package edu.upc.eetac.dsa.dsaqp1314.AnakinKarts.api.model;

import java.util.ArrayList;
import java.util.List;

public class EventoCollection {
	

	private List<Evento> eventos;

	public EventoCollection() {
		super();
		eventos = new ArrayList<Evento>();
	}
	public List<Evento> getUsers() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}
	public void addEvento(Evento evento) {
		eventos.add(evento);
	}
	

}
