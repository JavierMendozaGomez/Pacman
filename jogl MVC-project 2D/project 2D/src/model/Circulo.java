package model;

import java.util.ArrayList;

public class Circulo extends Figura{

	private PV2D centro;
	private tIntOut tIntOut;

	public Circulo(double x, double y ,double radio){
		super.listaPuntos = new ArrayList<PV2D>();
		super.x = x;
		super.y = y;
		super.radio = radio;
		this.centro = new PV2D(x,y);
		generaCirculo(x, y, radio);
	
	}
	
	private void generaCirculo(double x, double y, double radio) {

		Lapiz lapiz = new Lapiz(x, y, 0); //Posición central del poligono
		double alfa = 1;	
		for(int i = 0; i < 360; i++){

			lapiz.forward(radio, false); //Movemos el lapiz la distancia radio en el angulo especificado
			PV2D pos = new PV2D();
			pos.setX(lapiz.getPosActual().getX());
			pos.setY(lapiz.getPosActual().getY());
			listaPuntos.add(pos); //Añadimos el punto
			lapiz.forward(-radio, false); //Movemos el lapiz de nuevo al centro
			lapiz.turn(alfa);
		}	
	}


	@Override
	protected ArrayList<PV2D> getListaPuntos() {
		return listaPuntos;
	}

	@Override
	protected boolean changeColor(PV2D pos) {
		boolean pintar = false;
		Vector v  = new Vector(centro,pos);
		if(v.getModulo() < radio){
			pintar = true;
		}
		return pintar;
	}

	@Override
	protected Segmento calcularInterseccion(Segmento segmento,	PV2D puntoInicialSegmento, PV2D puntoFinalSegmento,	boolean interseccion) {
		
		if(hayInterseccion(segmento)){
			Vector  vIn = segmento.getVector().multiplicaVector(tIntOut.getTIn());       // Calculamos el porcentaje del tIn  respecto al segmento anterior
			Vector 	vOut = segmento.getVector().multiplicaVector(tIntOut.getTOut());     // Calculamos el porcentaje del tOut  respecto al segmento anterior

			puntoFinalSegmento.setX(puntoInicialSegmento.getX() + vOut.getX()); //Sumamos la cantidad a el punto inicial
			puntoFinalSegmento.setY(puntoInicialSegmento.getY() + vOut.getY());/// primero el puntoFinal porque el puntoInicial se sobreescribe
			puntoInicialSegmento.setX(puntoInicialSegmento.getX() + vIn.getX());
			puntoInicialSegmento.setY(puntoInicialSegmento.getY() + vIn.getY());

			segmento =  new Segmento(puntoInicialSegmento.getX(),puntoInicialSegmento.getY()////Creamos el nuevo segmento activo
					,puntoFinalSegmento.getX(),puntoFinalSegmento.getY(), true);
		}

		return segmento;
	}
	protected boolean hayInterseccion(Segmento segmento){
		
		boolean intersec = true;
		double A,B,C;
		double discriminante;
		double t1,t2;
		tIntOut = new tIntOut(0, 1);
		
		
		Vector CP = new Vector(centro,segmento.getInitPoint());
		Vector v = segmento.getVector();
		
		A = getProductoEscalar(v, v);
		B = 2 * (getProductoEscalar(CP, v));	
		C = getProductoEscalar(CP, CP) - Math.pow(radio, 2);

		
		discriminante = Math.pow(B, 2) - (4*A*C);
		
		if(discriminante <= 0 ){
			intersec = false;
			}
		
		else{
			 
			  t1 = (-B - Math.sqrt(discriminante))/(2*A);
			  if(t1 > tIntOut.getTIn())						/// el maximo entre los 2;
				  tIntOut.setTIn(t1);
			  t2 = (-B + Math.sqrt(discriminante))/(2*A);
			  if(t2 < tIntOut.getTOut())
				  tIntOut.setTOut(t2);  						//el minimo entre los 2
			}
		return intersec;
	}

	
}
