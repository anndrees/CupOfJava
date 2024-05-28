package model;

public class Fruta extends Articulo{

	public Fruta(String foto, String nombre, double precio){
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
