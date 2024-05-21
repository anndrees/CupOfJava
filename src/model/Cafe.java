package model;

public class Cafe extends Articulo{

	public Cafe(String nombre, double precio) {
		super(nombre, precio);
	}

	@Override
	public String getNombre(){
		return nombre;
	}

	@Override
	public double getPrecio(){
		return precio;
	}

}
