package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model;

import java.util.ArrayList;
import java.util.List;



public class EventoCollection {
	

	private List<Evento> eventos;
	
	private List<Evento> images = new ArrayList<Evento>();

	public List<Evento> getImages() {
		return images;
	}

	public void setImages(List<Evento> images) {
		this.images = images;
	}

	public void addImage(Evento image) {
		images.add(image);
	}

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
