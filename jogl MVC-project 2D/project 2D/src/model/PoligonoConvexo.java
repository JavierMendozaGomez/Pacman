package model;

import java.util.ArrayList;


public class PoligonoConvexo extends Figura{

	private int numVertices;
	private ArrayList<Vector> listaVectores;
	private ArrayList<Vector> listaNormales;
	private tIntOut tIntOut;
	

	public PoligonoConvexo(int numVertices, double x, double y, double radio){ //X e Y marcaran el punto central a traves del cual se generara el poligono
		super.listaPuntos = new ArrayList<PV2D>();
		this.listaVectores = new ArrayList<Vector>();
		this.listaNormales = new ArrayList<Vector>();
		this.numVertices = numVertices;
		
		generarPoligono(x, y, radio);
		generaVectores();
	}

	private void generarPoligono(double x, double y, double radio){
		Lapiz lapiz = new Lapiz(x, y, 0); //Posición central del poligono
		double alfa = 360 / numVertices;
		//double beta = (180 - alfa) / 2;
		//double alfa = 2*Math.PI/ numVertices; //Angulo central (Pico del quesito)
		//double beta = (Math.PI - alfa) / 2; //Los otros 2 angulos		

		for(int i = 0; i < numVertices; i++){

			lapiz.forward(radio, false); //Movemos el lapiz la distancia radio en el angulo especificado
			PV2D pos = new PV2D();
			pos.setX(lapiz.getPosActual().getX());
			pos.setY(lapiz.getPosActual().getY());
			listaPuntos.add(pos); //Añadimos el punto
			lapiz.forward(-radio, false); //Movemos el lapiz de nuevo al centro
			lapiz.turn(alfa);
		}		
	}

	public int getNumVertices() { return this.numVertices; }

	public ArrayList<PV2D> getListaPuntos(){ 
		return listaPuntos;
	}

	public ArrayList<Vector> getListaVectores(){
		return this.listaVectores;
	}


	public ArrayList<Vector> getListaNormales(){
		return this.listaNormales;
	}

	private void generaVectores(){
		PV2D posIni = new PV2D(listaPuntos.get(0).getX(),listaPuntos.get(0).getY());
		PV2D posFin;

		for(int i = 1; i < listaPuntos.size(); i++){									///une 0 - 1 y 1-2 pero no 2-0
			posFin = new PV2D(listaPuntos.get(i).getX(),listaPuntos.get(i).getY());	
			Vector v = new Vector(posIni, posFin);								//Dados dos puntos genera un vector
			listaVectores.add(v);											//Añadimos  a la lista de vectores	
			listaNormales.add(v.getNormalDrch());
			posIni = new PV2D();
			posIni.setX(posFin.getX());													
			posIni.setY(posFin.getY());												
		}

		posFin = new PV2D(listaPuntos.get(0).getX(),listaPuntos.get(0).getY());		//añade el ultimo vector (2-0)
		Vector v = new Vector(posIni, posFin);								         //Dados dos puntos genera un vector
		listaVectores.add(v);
		listaNormales.add(v.getNormalDrch());
	}

	public Segmento calcularInterseccion(Segmento segmento, PV2D puntoInicialSegmento, PV2D puntoFinalSegmento, boolean interseccion) {

		if(cyrusBeck(segmento, interseccion)){
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

	private boolean cyrusBeck(Segmento segmento, boolean interseccion){

		tIntOut = new tIntOut(0,1);

		int i = 0;
		double tHit,numerador, denominador;
		boolean encontrado = false;

		while(!encontrado && i < listaPuntos.size()){

			Vector PiP = new Vector (listaPuntos.get(i), segmento.getInitPoint());/// vector de P(i) hasta el punto inicial
			numerador = getProductoEscalar(PiP.negarVector(),listaNormales.get(i)); // 
			denominador = getProductoEscalar(listaNormales.get(i),segmento.getVector()); // V*Ni ,Si es 0 hay paralelismo 
			if( denominador == 0 ){					//Si hay paralelismo o coincidencia
				encontrado = true;
			}
			else {
				tHit = numerador/denominador;
				if(denominador < 0){			///Si el tHit es de entrada
					if(tHit > tIntOut.getTIn())			//El maximo entre tIn y tHit
						tIntOut.setTIn(tHit);
				}

				else if(denominador > 0){			///Si el tHit es de salida
					if(tHit < tIntOut.getTOut())			///El minimo entre tOut y tHit
						tIntOut.setTOut(tHit);
				}
				encontrado = tIntOut.getTIn() > tIntOut.getTOut(); 
				i++;
			}
		}
		interseccion = !encontrado;
		return !encontrado;					//devuelve true si hay interseccion
	}

	public boolean changeColor(PV2D posRaton){

		double angulo;
		boolean pintar = false;

		pintar = true;
		ArrayList<Vector> listaAuxiliarVectores = new ArrayList<Vector>();

		for (int j = 0; j < this.listaPuntos.size(); j++) {					//creamos todos los vectores a partir de todos los puntos
			PV2D in = new PV2D(this.listaPuntos.get(j).getX(), this.listaPuntos.get(j).getY());
			Vector v = new Vector(in, posRaton);
			listaAuxiliarVectores.add(v);
		}	

		for (int k = 0; k < listaNormales.size(); k++) {
			angulo = getProductoEscalar(listaNormales.get(k), listaAuxiliarVectores.get(k));
			if(angulo > 0 ){
				pintar = false;
			}
		}
    	return pintar;
	}
	
}

	





