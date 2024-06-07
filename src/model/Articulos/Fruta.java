package model.Articulos;

import model.Articulo;
import model.Categorias;

public class Fruta extends Articulo{

	public Fruta(String nombre, double precio){
		super(nombre, precio);
		this.categoria = Categorias.FRUTAS.toString();
	}
}
