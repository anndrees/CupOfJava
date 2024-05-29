package model;

import javafx.scene.image.Image;

public class Cafe extends Articulo{

	public Cafe(Image foto, String nombre, double precio){
		super(foto, nombre, precio);
		this.categoria = Categorias.CAFES.toString();
		System.out.println(Categorias.CAFES.toString());
	}


	@Override
	public Image getFoto() {
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

	@Override
	public String getCategoria(){
		return this.categoria;
	}
}
