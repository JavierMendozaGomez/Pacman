package model;

public class PV2D {
	
	private double x;
	private double y;
	
	public PV2D(){
		this.x = 0;
		this.y = 0;
	}
	
	
	public PV2D(double x, double y){ //------------------------------>OJO!
		this.x = x;
		this.y = y;
	}
	
	
	public PV2D(PV2D p){
		this.x = p.getX();
		this.y = p.getY();
	}
	
	public double getX(){ return this.x; }
	public double getY(){ return this.y; }
	
	public void setX(double x){ this.x = x; }
	public void setY(double y){ this.y = y; }
	
	public String toString(){
		return "PV2D x:" + this.x + " y:" +this.y;
	}
}
