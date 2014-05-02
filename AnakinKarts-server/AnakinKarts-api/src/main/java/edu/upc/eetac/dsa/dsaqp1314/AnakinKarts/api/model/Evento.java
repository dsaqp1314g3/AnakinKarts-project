package edu.upc.eetac.dsa.dsaqp1314.AnakinKarts.api.model;

public class Evento {
	
	int eventoid=0;
	int numpersonas=0;
	String fecha=null;
	String pista=null;
	int mejorvuelta=0;
	String ganador=null;
	
	

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
	public String getPista() {
		return pista;
	}
	public void setPista(String pista) {
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
	public String getGanador() {
		return ganador;
	}
	public void setGanador(String ganador) {
		this.ganador = ganador;
	}


}
