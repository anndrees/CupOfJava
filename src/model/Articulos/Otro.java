package model.Articulos;

import model.Articulo;
import model.Categorias;

public class Otro extends Articulo{
	public Otro(String nombre, double precio){
		super(nombre, precio);
		this.categoria = Categorias.OTROS.toString();
	}
}
