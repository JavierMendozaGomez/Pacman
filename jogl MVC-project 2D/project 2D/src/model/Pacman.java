package model;

import java.util.ArrayList;

public class Pacman {

	private ArrayList<PV2D> listaPuntos;
	private double radio;
	private PV2D centro; 
	double numPuntos;
	double grados;
	boolean sentido;
	public static int GRADOS = 14;
	public static boolean SENTIDO_GRADOS = true;
	public Pacman(double x, double y ,double radio, boolean sentido){
		this.listaPuntos = new ArrayList<PV2D>();
		
		this.radio = radio;
		this.centro = new PV2D(x,y);
		this.sentido = sentido;
		if(sentido)
			generaPacman(x, y, radio);
		else
			generaPacmanContrario(x,y,radio);
	
	}
	
	private void generaPacmanContrario(double x, double y, double radio2) {
		Lapiz lapiz = new Lapiz(x, y, 0); //Posición central del poligono
		double alfa = 1;	
		
		if(SENTIDO_GRADOS)
			GRADOS = GRADOS +2;
		else
			GRADOS = GRADOS -2;
		if(GRADOS == 46 || GRADOS == 0){ //Si la posicion actual es 0 o N cambiamos el sentido
			SENTIDO_GRADOS = !SENTIDO_GRADOS;
		}
		

		this.numPuntos = 180- (GRADOS);
		for(int i = 0; i < numPuntos; i++){
			lapiz.forward(radio, false); //Movemos el lapiz la distancia radio en el angulo especificado
			PV2D pos = new PV2D();
			pos.setX(lapiz.getPosActual().getX());
			pos.setY(lapiz.getPosActual().getY());
			listaPuntos.add(pos); //Añadimos el punto
			lapiz.forward(-radio, false); //Movemos el lapiz de nuevo al centro
			lapiz.turn(alfa);
		}
		listaPuntos.add(centro);
		lapiz.turn(GRADOS*2);
		for(int i = 0; i < numPuntos; i++){
			lapiz.forward(radio, false); //Movemos el lapiz la distancia radio en el angulo especificado
			PV2D pos = new PV2D();
			pos.setX(lapiz.getPosActual().getX());
			pos.setY(lapiz.getPosActual().getY());
			listaPuntos.add(pos); //Añadimos el punto
			lapiz.forward(-radio, false); //Movemos el lapiz de nuevo al centro
			lapiz.turn(alfa);
		}
		
	}

	private void generaPacman(double x, double y, double radio) {

		Lapiz lapiz = new Lapiz(x, y, 0); //Posición central del poligono
		double alfa = 1;	
		
		if(SENTIDO_GRADOS)
			GRADOS = GRADOS +2;
		else
			GRADOS = GRADOS -2;
		if(GRADOS == 46 || GRADOS == 0){ //Si la posicion actual es 0 o N cambiamos el sentido
			SENTIDO_GRADOS = !SENTIDO_GRADOS;
		}
		
		listaPuntos.add(centro);	
		lapiz.turn(GRADOS);
		this.numPuntos = 360 - (GRADOS*2);
		for(int i = 0; i < numPuntos; i++){
			lapiz.forward(radio, false); //Movemos el lapiz la distancia radio en el angulo especificado
			PV2D pos = new PV2D();
			pos.setX(lapiz.getPosActual().getX());
			pos.setY(lapiz.getPosActual().getY());
			listaPuntos.add(pos); //Añadimos el punto
			lapiz.forward(-radio, false); //Movemos el lapiz de nuevo al centro
			lapiz.turn(alfa);
		}
	}

	public void compruebaPacman(Segmento segmento,boolean sentido){
		ArrayList<PV2D> lista = segmento.getListaPuntos();
		if(lista.size() > 0){
			
		if(sentido){
		Vector v  = new Vector(lista.get(0),centro);
		if(v.getModulo() < radio)
			segmento.eliminaPrimero();
		}
		else{
			Vector v  = new Vector((lista.get(lista.size() -1)),centro);
			if(v.getModulo() < radio)
				segmento.eliminaUltimo();
		}
		}			
	}
	protected ArrayList<PV2D> getListaPuntos() {
		return listaPuntos;
	}
}
