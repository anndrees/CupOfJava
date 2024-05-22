package model;

public class Cafe extends Articulo{

	public Cafe(String foto, String nombre, double precio){
		super(foto, nombre, precio);
	}


	@Override
	public String getFoto() {
		return this.foto;
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public double getPrecio() {
		return this.precio;
	}
}
