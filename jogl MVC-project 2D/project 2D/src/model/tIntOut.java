package model;

public class tIntOut {

	public double tIn, tOut;
	
	public tIntOut(double tIn,double tOut) {
		this.tIn = tIn;
		this.tOut = tOut;
	}
	
	public void setTIn(double tIn){
		this.tIn = tIn;
	}
	
	public void setTOut(double tOut){
		this.tOut = tOut;
	}
	
	public double getTIn(){
		return this.tIn;
	}
	
	public double getTOut(){
		return this.tOut;
	}
}
