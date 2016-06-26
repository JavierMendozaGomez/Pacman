package controller;

import java.util.LinkedList;

import model.Segmento;

public class Lista {
	LinkedList<Segmento> lista;
	
	public Lista(){
		this.lista = new LinkedList<Segmento>();
	}
	
	public void addSegment(Segmento seg){
		lista.add(seg);
	}
	
	public Segmento getSegmento(int i){
		return lista.get(i);
	}
	
	public int getSize(){
		return lista.size();
	}
}
