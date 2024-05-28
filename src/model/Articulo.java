package model;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		Articulo articulo = (Articulo) o;
		return Double.compare(precio, articulo.precio) == 0 && Objects.equals(foto, articulo.foto) && Objects.equals(nombre, articulo.nombre);
	}

	@Override
	public int hashCode(){
		return Objects.hash(foto, nombre, precio);
	}
}

