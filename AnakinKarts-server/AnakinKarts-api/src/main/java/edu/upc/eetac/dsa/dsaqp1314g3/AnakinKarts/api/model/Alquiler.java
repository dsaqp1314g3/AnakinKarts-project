package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model;

public class Alquiler {
	
	int alquilerid= 0;
	String organizador = null;
	String fecha= null;
	int numplayers= 0;
	int pista = 0;
	
	public int getAlquilerid() {
		return alquilerid;
	}
	public void setAlquilerid(int alquilerid) {
		this.alquilerid = alquilerid;
	}
	public String getOrganizador() {
		return organizador;
	}
	public void setOrganizador(String organizador) {
		this.organizador = organizador;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getNumplayers() {
		return numplayers;
	}
	public void setNumplayers(int numplayers) {
		this.numplayers = numplayers;
	}
	public int getPista() {
		return pista;
	}
	public void setPista(int pista) {
		this.pista = pista;
	}

	

}
