package model;

public abstract class Articulo{

	public String foto;
	public String nombre;
	public double precio;

	public Articulo(String foto, String nombre, double precio){
		this.foto = foto;
		this.nombre = nombre;
		this.precio = precio;
	}


	public abstract String getFoto();

	public abstract String getNombre();
	public abstract double getPrecio();
}
