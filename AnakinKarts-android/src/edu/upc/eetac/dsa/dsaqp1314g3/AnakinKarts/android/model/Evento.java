package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.android.model;

import java.util.ArrayList;
import java.util.List;

public class Evento {
	
	int eventoid =  0;
	int numpersonas=0;
	String fecha=null;
	int pista=0;
	int mejorvuelta=0;
	String organizador=null;
	String ganador=null;
	String nombre =null;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	List<String> jugadores=new ArrayList<String>();
	

	public int getNumpersonas() {
		return numpersonas;
	}
	public void setNumpersonas(int numpersonas) {
		this.numpersonas = numpersonas;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getPista() {
		return pista;
	}
	public void setPista(int pista) {
		this.pista = pista;
	}
	public int getEventoid() {
		return eventoid;
	}
	public void setEventoid(int eventoid) {
		this.eventoid = eventoid;
	}
	public int getMejorvuelta() {
		return mejorvuelta;
	}
	public void setMejorvuelta(int mejorvuelta) {
		this.mejorvuelta = mejorvuelta;
	}
	public String getGanador() {		return ganador;
	}
	public void setGanador(String ganador) {
		this.ganador = ganador;
	}
	public List<String> getJugadores() {
		return jugadores;
	}
	public void setJugadores(List<String> jugadores) {
		this.jugadores = jugadores;
	}
	public void addJugadores(String jugador){
		jugadores.add(jugador);
	}
	public String getOrganizador() {
		return organizador;
	}
	public void setOrganizador(String organizador) {
		this.organizador = organizador;
	}

	
	


}
