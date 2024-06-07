package model.Articulos;

import model.Articulo;
import model.Categorias;

public class Refresco extends Articulo{
	public Refresco(String nombre, double precio){
		super(nombre, precio);
		this.categoria = Categorias.REFRESCOS.toString();
	}
}
