package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Ticket{
	private ArrayList<Articulo> articulos;
	private ArrayList<Integer> cantidades;

	public Ticket(ArrayList<Articulo> articulos, ArrayList<Integer> cantidades){
		this.articulos = articulos;
		this.cantidades = cantidades;
	}

	public ArrayList<Articulo> getArticulos(){
		return articulos;
	}

	public ArrayList<Integer> getCantidades(){
		return cantidades;
	}
}
