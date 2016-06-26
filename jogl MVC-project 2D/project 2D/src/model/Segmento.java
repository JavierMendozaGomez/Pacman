package model;

import java.util.ArrayList;

public class Segmento {
	
	private PV2D initPoint;
	private PV2D finPoint;
	private Vector v;
	private boolean interseccion;
	
	private double dir;
	private double dist;
	private ArrayList<PV2D> lista;
	private ArrayList<PV2D> listaAuxiliar;
	
	public Segmento(double xIni, double yIni, double xFin, double yFin, boolean interseccion){
		this.initPoint = new PV2D(xIni, yIni);
		this.finPoint = new PV2D(xFin, yFin);
		this.v = new Vector(this.initPoint, this.finPoint);
	    lista = new ArrayList<PV2D>();
		this.interseccion = interseccion;
		inicializaPuntos();
	}
	
	
	public PV2D getInitPoint(){
		return this.initPoint;
	}
	
	public PV2D getFinalPoint(){
		return this.finPoint;
	}
	
	public Vector getVector(){
		return this.v;
	}
	
	public double getDir(){
		return this.dir;
	}
	
	public double getDist(){
		return this.dist;
	}
	
	public boolean getInterseccion(){ //Lo usaremos para saber si el segmento es interseccion de un poligono
		return this.interseccion;
	}
	
	public void eliminaPrimero(){
		lista.remove(0);
	}
	public void eliminaUltimo(){
		lista.remove(lista.size()-1);
	}
	
	@SuppressWarnings("unchecked")
	public void inicializaPuntos(){
	this.listaAuxiliar =  new ArrayList<PV2D>();

		PV2D posIni = getInitPoint();
		lista.add(posIni);
		double porcentaje =  0.2;
		
		while(porcentaje <= 1){
			Vector vAux = v.multiplicaVector(porcentaje);
			PV2D pos = new PV2D();

			pos.setX(posIni.getX() + vAux.getX());
			pos.setY(posIni.getY() + vAux.getY());

			lista.add(pos);
			porcentaje = porcentaje + 0.2;
		}
		listaAuxiliar = (ArrayList<PV2D>)lista.clone();
	
	}

	@SuppressWarnings("unchecked")
	public void resetLista(){
		this.lista = (ArrayList<PV2D>) listaAuxiliar.clone();
	}
	
	public ArrayList<PV2D> getListaPuntos(){
		return this.lista;
	}

}
