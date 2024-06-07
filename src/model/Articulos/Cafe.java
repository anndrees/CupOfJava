package model.Articulos;

import model.Articulo;
import model.Categorias;

public class Cafe extends Articulo{

	public Cafe(String nombre, double precio){
		super(nombre, precio);
		this.categoria = Categorias.CAFES.toString();
	}
}
