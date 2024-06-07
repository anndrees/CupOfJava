package model.Articulos;

import model.Articulo;
import model.Categorias;

public class Postre extends Articulo{
	public Postre(String nombre, double precio){
		super(nombre, precio);
		this.categoria = Categorias.POSTRES.toString();
	}
}
