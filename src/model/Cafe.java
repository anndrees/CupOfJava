package model;

import javafx.scene.image.Image;

public class Cafe extends Articulo{

	public Cafe(String nombre, double precio){
		super(nombre, precio);
		this.categoria = Categorias.CAFES.toString();
	}


	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public double getPrecio() {
		return this.precio;
	}

	@Override
	public String getCategoria(){
		return this.categoria;
	}

	public void setNombre(String nombre) {
		super.setNombre(nombre);
	}

	public void setPrecio(double precio) {
		super.setPrecio(precio);
	}
}
