package model;

public class Lapiz{

	private PV2D pos;
	private double dir; //En radianes ---------------------------OJO!
	//Ángulo que forma la recta paralela al eje x que pasa por pos

	//Constructores
	public Lapiz(){
		this.pos = new PV2D(); //Origen. Coordenadas (0, 0)
		this.dir = 0;
	}

	public Lapiz(double x, double y, double dir){ //OJO! se pasa x e y o diractamente un PV2D?
		this.pos = new PV2D(x, y);
		this.dir = dir;		
	}
	
	public Lapiz(PV2D p, double dir){
		this.pos = p; //o this.pos = new PV2D(p);
		this.dir = dir;
	}

	//Setters

	public void moveTo(PV2D p, boolean draw){
		this.pos = p; //Trasladamos la posición del lapiz hasta p
		//draw indica si se dibuja el segmento o no -------------------------------------------OJO!
	}

	public void turnTo(double a){ //Expresado en radianes o en ángulos, pero que sean iguales
		this.dir = a;
	}

	//Getters

	public PV2D getPosActual(){
		return this.pos;
	}

	public double getDirActual(){
		return this.dir;
	}

	//Funcionalidad de la clase Lapiz

	public void turn(double a){
		this.dir += a;
		//a > 0 giramos a la izq
		//a < 0 giramos a la drch
	}

	public void forward(double dist, boolean draw){
		double x = this.pos.getX(); //Inicializamos x e y
		double y = this.pos.getY();
		
		x += dist * Math.cos(this.dir*Math.PI/180);	//Obtenemos la nueva posición	
		y += dist * Math.sin(this.dir*Math.PI/180);

		pos.setX(x); //Guardamos la nueva posición
		pos.setY(y);
	}
	
	
	//Falta draw?--------------------------------------------------------OJO
}
