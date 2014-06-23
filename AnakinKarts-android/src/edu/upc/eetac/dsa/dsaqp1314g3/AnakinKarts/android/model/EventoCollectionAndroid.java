package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android.model;

import java.util.ArrayList;
import java.util.List;

public class EventoCollectionAndroid {
	private List<Eventoandroid> eventosa;
	
	public EventoCollectionAndroid() {
		super();
		eventosa = new ArrayList<Eventoandroid>();
	}
	public List<Eventoandroid> getEventosa() {
		return eventosa;
	}
	public void setEventosa(List<Eventoandroid> eventosa) {
		this.eventosa = eventosa;
	}
	public void addEventoandroid (Eventoandroid eventoa)
	{
		eventosa.add(eventoa);
	}
}
