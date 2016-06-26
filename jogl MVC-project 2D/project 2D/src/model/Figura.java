package model;

import java.util.ArrayList;


public abstract class Figura {

	protected double radio;
	protected double x;
	protected double y;
	protected ArrayList<PV2D> listaPuntos;
	
	protected abstract boolean changeColor(PV2D pos);
	protected abstract ArrayList<PV2D> getListaPuntos();
	protected abstract Segmento calcularInterseccion(Segmento segmento, PV2D puntoInicialSegmento, PV2D puntoFinalSegmento, boolean interseccion);
	protected double getProductoEscalar(Vector v1,Vector v2){									/// devuelve el producto escalar
		double escalar = (v1.getX() * v2.getX())+ (( v1.getY() * v2.getY())); 				// de los 2 vectores pasados por parametro
		return escalar;
	}
   


}
