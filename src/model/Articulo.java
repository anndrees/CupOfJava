package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Articulo{

	private final List<Articulo> articulos = new ArrayList<>();

	public String nombre;
	public double precio;
	public String categoria;

	public Articulo(String nombre, double precio){
		this.nombre = nombre;
		this.precio = precio;

	}


	public String getNombre(){
		return this.nombre;
	}
	public double getPrecio(){
		return this.precio;
	}

	public String getCategoria(){
		return this.categoria;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}


	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		Articulo articulo = (Articulo) o;
		return Double.compare(precio, articulo.precio) == 0 && Objects.equals(nombre, articulo.nombre) && Objects.equals(categoria, articulo.categoria);
	}

	@Override
	public int hashCode(){
		return Objects.hash(nombre, precio, categoria);
	}

	public ObservableList<Articulo> getArticulos() {
		return FXCollections.observableList(articulos);

	}
}

