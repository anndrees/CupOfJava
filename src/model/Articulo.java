package model;

import javafx.scene.image.Image;

import java.util.Objects;

public abstract class Articulo{

	public String nombre;
	public double precio;
	public String categoria;

	public Articulo(String nombre, double precio){
		this.nombre = nombre;
		this.precio = precio;

	}


	public abstract String getNombre();
	public abstract double getPrecio();

	public abstract String getCategoria();

	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		Articulo articulo = (Articulo) o;
		return Double.compare(precio, articulo.precio) == 0 && Objects.equals(nombre, articulo.nombre);
	}

	@Override
	public int hashCode(){
		return Objects.hash(nombre, precio);
	}
}

