package model;

public class Vector {

	private double x;
	private double  y;
	private PV2D ptoIni,ptoFin;

	public Vector(PV2D ptoIni, PV2D ptoFin) { // o a partir de dos puntos
		this.ptoIni = ptoIni;
		this.ptoFin = ptoFin;
		this.x = ptoFin.getX() - ptoIni.getX();
		this.y =  ptoFin.getY() - ptoIni.getY() ;
	}

	
	public PV2D getPtoIni() {
		return ptoIni;
	}


	public void setPtoIni(PV2D ptoIni) {
		this.ptoIni = ptoIni;
	}


	public PV2D getPtoFin() {
		return ptoFin;
	}


	public void setPtoFin(PV2D ptoFin) {
		this.ptoFin = ptoFin;
	}


	public Vector(double x, double y) { // / o creada por la normal por la izda
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
	public void setY(double y){
		this.y = this.y + y;
	}
	public void setX(double x){
		this.x = this.x + x;
	}

	public String toString() {
		return "EL VECTOR ES   X: " + this.x + " Y " + this.y;
	}

	public double getModulo() {
		double modulo = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		return modulo;
	}

	public Vector getNormalIzda() { // normal por la izquierda v^T AL REVES
		return new Vector(-this.y, this.x);
	}
	
	public Vector getNormalDrch(){ // normal hacia fuera, por la derecha
		return new Vector(this.y, -this.x);
	}
	public Vector negarVector(){
		return new Vector(-this.x, -this.y);
	}
	public Vector multiplicaVector(double num){
		return new Vector(this.x * num, this.y*num);
	}
}
