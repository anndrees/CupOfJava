package model;

public abstract class Articulo{

	public String nombre;
	public double precio;

	public Articulo(String nombre, double precio){
		this.nombre = nombre;
		this.precio = precio;
	}



	public abstract String getNombre();
	public abstract double getPrecio();
}
