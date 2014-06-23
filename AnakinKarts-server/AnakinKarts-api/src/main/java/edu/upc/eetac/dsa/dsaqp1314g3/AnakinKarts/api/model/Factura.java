package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model;

public class Factura {
	String organizador=null;
	int alquilerid=0;
	int facturaid=0;
	double precio=0;
	
	public int getFacturaid() {
		return facturaid;
	}
	public void setFacturaid(int facturaid) {
		this.facturaid = facturaid;
	}
	
	public String getOrganizador() {
		return organizador;
	}
	public void setOrganizador(String organizador) {
		this.organizador = organizador;
	}
	public int getAlquilerid() {
		return alquilerid;
	}
	public void setAlquilerid(int alquilerid) {
		this.alquilerid = alquilerid;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	

}
