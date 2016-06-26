//////////////////////////////////////////// 
// Project skeleton for Computer 2D Graphics
// MVC-based design
// Author: Chus Martï¿½n
// 2014
//////////////////////////////////////////// 

package model;

//JOGL imports
///Autor@: Juan Antonio de la Vega Gutierrez & Javier Mendoza Gomez

import java.util.ArrayList;



import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;


public class Scene {

	// Scene Visible Area (SVA)
	private double xLeft, xRight, yTop, yBottom; // SVA position

	// Scene variables
	private double xCenter;
	private double yCenter ;
	private double escalaAncho, escalaAlto;


	private int FiguraPintada;
	private PoligonoConvexo triangulo;
	private PoligonoConvexo cuadrado;
	private PoligonoConvexo pentagono;
	private PoligonoConvexo hexagono;
	private Circulo circulo;
	
	private  Pacman pacman;


	private ArrayList<Figura> arrayFiguras;
	private ArrayList<Circulo> arrayBolas;

	private double incrX, incrY;

	//Atributos Intersección
	private Segmento segmento;
	private PV2D puntoInicialSegmento, puntoFinalSegmento;
	private boolean interseccion;
	//Atributos pelota
	private PV2D puntoPelota;
	private int N;			//Nº de particiones que tengo que recorrer
	private int currentN;
	private boolean animando;
	private boolean sentido;
	private Vector vectorSegmento;

	private boolean puntos;
	/////////////////////////////////
	public Scene(double xLeft1, double xRight1, double yTop1, double yBottom1){
		// SVA
		xLeft= xLeft1;
		xRight= xRight1;

		yTop= yTop1;
		yBottom= yBottom1;

		xCenter = (xRight - xLeft)/2;
		yCenter  = (yTop - yBottom)/2;

		incrX = 0;
		incrY = 0;

		this.arrayFiguras = new ArrayList<Figura>();
		
		this.arrayBolas = new ArrayList<Circulo>();
		
		this.FiguraPintada = -1;
		this.interseccion = false;
		//Inicializamos algunos valores de la pelota
		this.N = 100;
		this.currentN = 0;
		this.animando = false;
		this.puntos = true;
		inicializarEscena();
	}

	/////////////////////////////////
	public double getXLeft()   { return xLeft;}
	public double getXRight()  { return xRight;}
	public double getYTop()    { return yTop;}
	public double getYBottom() { return yBottom;}

	public double getWidth()   { return xRight-xLeft;}
	public double getHeight()  { return yTop-yBottom;}

	public double getCentroX() { return (xLeft+xRight)/2.0;}
	public double getCentroY() { return (yBottom+yTop)/2.0;}	


	/////////////////////////////////
	public void resize(double viewPortRatio){		
		double sceneVisibleAreaRatio=(xRight-xLeft)/(yTop-yBottom);

		if (sceneVisibleAreaRatio>=viewPortRatio){
			// Increase SVA height
			double newHight= (xRight-xLeft)/viewPortRatio;
			double yCenter= (yBottom+yTop)/2.0;
			yTop= yCenter + newHight/2.0;
			yBottom= yCenter - newHight/2.0;
		}
		else{
			// Increase SVA width
			double newWidth= viewPortRatio*(yTop-yBottom);
			double xCenter= (xLeft+xRight)/2.0;
			xRight= xCenter + newWidth/2.0;
			xLeft= xCenter - newWidth/2.0;
		}
	}

	public void actualizar(double anchoNew, double altoNew){


		xRight = xCenter + anchoNew/2.0;
		xLeft =  xCenter - anchoNew/2.0;

		yTop = yCenter + altoNew/2.0;
		yBottom = yCenter - altoNew/2.0;

		System.out.println(xRight + "  " + xLeft + "  " + yTop + "  " + yBottom + "     " + anchoNew + " " + altoNew);
	}

	/////////////////////////////////
	public void draw(GLAutoDrawable drawable){

		GL2 gl = drawable.getGL().getGL2();

		///FONDO
		gl.glColor3f(0.0f,0.0f,0.0f);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2d(xRight, yBottom);
		gl.glVertex2d(xRight, yTop);
		gl.glVertex2d(xLeft, yTop);
		gl.glVertex2d(xLeft, yBottom);
		gl.glEnd();
		
		
		PV2D pos;
		ArrayList<PV2D> listaPuntos;
		
		////FIGURAS
		for(int i = 0; i < arrayFiguras.size(); i++){//Recorre array de poligonos
			gl.glBegin(GL2.GL_LINE_LOOP);
			listaPuntos = arrayFiguras.get(i).getListaPuntos();/// obtenemos la lista del primer dibujo

			if(i == FiguraPintada) gl.glColor3f(0.2f,0.8f,0.0f);
			else
				gl.glColor3f(0.0f,0.0f,0.9f);
			
			for(int j = 0; j < listaPuntos.size(); j++){	// recorremos todos los vertices
				pos = listaPuntos.get(j);
				gl.glVertex2d(pos.getX() -3 , pos.getY() - 3);
			}
			if(i == FiguraPintada) gl.glColor3f(0.0f,0.0f,0.0f);
			gl.glEnd();
		}
		
		//SEGMENTO
		
		if((puntoFinalSegmento != null)&&(puntoInicialSegmento!=null)){

			if(puntos){
			gl.glColor3f(1.0f,1.0f,1.0f);
			if(interseccion){
				gl.glColor3f(1.0f, 0.0f, 0.0f);
			}
			for(int i = 0; i < arrayBolas.size();i++){
				Circulo bola = arrayBolas.get(i);
				gl.glBegin(GL2.GL_POLYGON);
				for (int j = 0; j < bola.getListaPuntos().size(); j++) {
					PV2D punto = bola.getListaPuntos().get(j);
					gl.glVertex2d(punto.getX(),punto.getY());
				}
				gl.glEnd();	
			}
			}
			else{
				gl.glColor3f(1.0f,1.0f,1.0f);
				if(interseccion){
					gl.glColor3f(1.0f, 0.0f, 0.0f);
				}
				gl.glBegin(GL.GL_LINE_LOOP);
				gl.glVertex2d(puntoInicialSegmento.getX(), puntoInicialSegmento.getY());
				gl.glVertex2d(puntoFinalSegmento.getX(), puntoFinalSegmento.getY());
				gl.glEnd();
			}
			
		}

		
		//PACMAN

		if(this.animando){
			gl.glColor3f(0.8f, 0.8f, 0.0f);
			gl.glBegin(GL2.GL_POLYGON);
			for (int i = 0; i < pacman.getListaPuntos().size(); i++) {
				gl.glVertex2d(pacman.getListaPuntos().get(i).getX(), pacman.getListaPuntos().get(i).getY());
			}
			gl.glEnd();
			
		}

		gl.glFlush();
	}

	private void inicializarEscena(){
		double radio = 50;
		this.triangulo = new PoligonoConvexo(3, xCenter, yCenter, radio);
		this.cuadrado = new PoligonoConvexo(4, xCenter + 175, yCenter + 50, radio);
		this.pentagono = new PoligonoConvexo(5, xCenter - 200, yCenter -50, radio);
		this.hexagono = new PoligonoConvexo(6, xCenter - 125, yCenter + 70, radio);
		////
		this.circulo = new Circulo(xCenter + 110, yCenter - 60, 42);
		//
		arrayFiguras.add(this.triangulo);
		arrayFiguras.add(this.cuadrado);
		arrayFiguras.add(this.pentagono);
		arrayFiguras.add(this.hexagono);
		arrayFiguras.add(this.circulo);
	}

	public void changeColor(double xRaton, double yRaton, int width, int height){
		
		FiguraPintada = -1;

		escalaAncho = width/(xRight - xLeft); 
		escalaAlto = height/(yTop - yBottom);

		//Como xRaton e yRaton obtendra la posicion del canvas (numeros enteros sin escala) debemos escalarlos para que su posicion sea la adecuada por si hay zooms
		xRaton = xRaton / escalaAncho;
		yRaton = yRaton / escalaAlto;  

		//Calculamos el incremento(positivo o negativo) que sufre x e y 
		incrX = xRaton - ((xRight - xLeft)/2);
		incrY = yTop - yRaton - yCenter;


		PV2D posRaton = new PV2D(this.xCenter + incrX, this.yCenter + incrY);

		int i = 0;
		while(i < arrayFiguras.size() && FiguraPintada == -1){
			if(arrayFiguras.get(i).changeColor(posRaton))
				FiguraPintada = i;
			else
			   i++;
		}
	}
	
	public void setCenter(double xRaton, double yRaton, int width, int height) {

		//Calculamos la escala, es decir la diferencia entre el tamaño del canvas y el de nuestra area visible
		escalaAncho = width/(xRight - xLeft); 
		escalaAlto = height/(yTop - yBottom);

		//Como xRaton e yRaton obtendra la posicion del canvas (numeros enteros sin escala) debemos escalarlos para que su posicion sea la adecuada por si hay zooms
		xRaton = xRaton / escalaAncho;
		yRaton = yRaton / escalaAlto;		

		//Calculamos el incremento(positivo o negativo) que sufre x e y 
		incrX = xRaton - ((xRight - xLeft)/2);
		incrY = yTop - yRaton - yCenter;

		//Sumamos a las 4 esquinas(aristas, bordes) el incremento y calculamos los nuevos centros respecto a estos nuevos bordes
		xRight += incrX;
		xLeft  += incrX;
		xCenter = (xLeft + xRight)/2.0;
		yTop   += incrY;
		yBottom+= incrY;
		yCenter = (yBottom + yTop)/2.0;


	}


	public void embaldosar(int nCol,GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		double SVAratio = getWidth()/getHeight();
		double w=(double)drawable.getWidth()/(double)nCol;
		double h = w/ SVAratio;
		for(int c=0; c<nCol;c++){
			double currentH = 0;
			while(((currentH + h )) <= drawable.getHeight()){	
				gl.glViewport((int)(c*w),(int)currentH,(int)w,(int)h);
				draw(drawable);
				currentH +=h;
			}//while
		}//for
	}

	public void segmentoInicial(double xRaton, double yRaton, int width, int height) {

		if(!this.animando){
			escalaAncho = width/(xRight - xLeft); 
			escalaAlto = height/(yTop - yBottom);

			//Como xRaton e yRaton obtendra la posicion del canvas (numeros enteros sin escala) debemos escalarlos para que su posicion sea la adecuada por si hay zooms
			xRaton = xRaton / escalaAncho;
			yRaton = yRaton / escalaAlto;  

			//Calculamos el incremento(positivo o negativo) que sufre x e y 
			incrX = xRaton - ((xRight - xLeft)/2);
			incrY = yTop - yRaton - yCenter;


			puntoInicialSegmento = new PV2D(this.xCenter + incrX, this.yCenter + incrY);
			interseccion = false;
			puntoFinalSegmento = null;		
		}
	}		

	public void segmentoFinal(double xRaton, double yRaton, int width, int height) {

		if(puntoInicialSegmento != null){
			escalaAncho = width/(xRight - xLeft); 
			escalaAlto = height/(yTop - yBottom);

			//Como xRaton e yRaton obtendra la posicion del canvas (numeros enteros sin escala) debemos escalarlos para que su posicion sea la adecuada por si hay zooms
			xRaton = xRaton / escalaAncho;
			yRaton = yRaton / escalaAlto;  

			//Calculamos el incremento(positivo o negativo) que sufre x e y 
			incrX = xRaton - ((xRight - xLeft)/2);
			incrY = yTop - yRaton - yCenter;

			puntoFinalSegmento = new PV2D(this.xCenter + incrX, this.yCenter + incrY);	

			this.segmento = new Segmento(this.puntoInicialSegmento.getX(), this.puntoInicialSegmento.getY(), this.puntoFinalSegmento.getX(), this.puntoFinalSegmento.getY(), false);
			interseccion = false;
			if(this.animando) this.inicializarPelota();
			actualizaBolas(segmento);

	
		}
	}

	public void  calcularInterseccion() {
		
		if((puntoInicialSegmento != null && puntoFinalSegmento != null) && FiguraPintada != -1){///Comprueba de que este pintado y de que haya un segmento
			this.segmento = arrayFiguras.get(FiguraPintada).calcularInterseccion(this.segmento, this.puntoInicialSegmento, this.puntoFinalSegmento, this.interseccion);
			this.interseccion = this.segmento.getInterseccion();
			if(this.animando) this.inicializarPelota(); //Por si se pulsa interseccion mientras animamos
			actualizaBolas(segmento);

		}
	}


	private void inicializarPelota(){
		//Inicializamos los valores de la pelota aqui por si se quieren modificar las coordenadas del segmento mientras se esta animanado
		this.vectorSegmento = new Vector(this.puntoInicialSegmento, this.puntoFinalSegmento);
		this.puntoPelota = new PV2D(puntoInicialSegmento.getX(), puntoInicialSegmento.getY());
		this.pacman = new Pacman(puntoPelota.getX() ,puntoPelota.getY(), 10,sentido);
		//this.pelota = new PoligonoConvexo(360, puntoPelota.getX(), puntoPelota.getY(), 10);
		this.currentN = 0;
		this.sentido = true; //True hacia "adelante". False hacia "atras"
	}

	public void start(){
		if(puntoInicialSegmento != null && puntoFinalSegmento != null){
			//Le damos valores al punto de la pelota
			if(this.puntoInicialSegmento != null && this.puntoFinalSegmento != null){
				this.animando = true;
				this.inicializarPelota();
			}
		}
	}


	public void step() {

		if(this.sentido){
			this.puntoPelota.setX(this.puntoPelota.getX() + this.vectorSegmento.getX()/N);
			this.puntoPelota.setY(this.puntoPelota.getY() + this.vectorSegmento.getY()/N);
			this.currentN++;

		}
		else{
			this.puntoPelota.setX(this.puntoPelota.getX() - this.vectorSegmento.getX()/N);
			this.puntoPelota.setY(this.puntoPelota.getY() - this.vectorSegmento.getY()/N);
			this.currentN--;

		}
		if(this.currentN == this.N || this.currentN == 0){ //Si la posicion actual es 0 o N cambiamos el sentido
			this.sentido = !this.sentido;
			segmento.resetLista();
		}
		this.pacman = new Pacman(puntoPelota.getX() ,puntoPelota.getY(), 12,sentido);
		pacman.compruebaPacman(segmento, sentido);
		actualizaBolas(segmento);
	}

	public void stop() {
		this.animando = false;
	}

	public boolean isAnimated(){return this.animando;}

	private void actualizaBolas(Segmento segmento){
		arrayBolas.clear();
		ArrayList<PV2D > lista = segmento.getListaPuntos();
		for (int i = 0; i < lista.size(); i++) {
			PV2D pos = lista.get(i);
			Circulo bola = new Circulo(pos.getX(), pos.getY(), 3);
			arrayBolas.add(bola);
		}
	}

	public void cambiaSegmento() {
		this.puntos = !this.puntos;
	}

}

