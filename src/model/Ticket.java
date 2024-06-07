package model;

import java.util.Objects;

public class Ticket{
	private Integer qty;
	private Articulo articulo;
	private Double total;


	public Ticket(Integer cantidad, Articulo articulo){
		this.qty = cantidad;
		this.articulo = articulo;
		this.total = this.qty * this.articulo.getPrecio();
	}

	public Integer getQty(){
		return qty;
	}

	public Articulo getArticulo(){
		return articulo;
	}

	public void setArticulo(Articulo articulo){
		this.articulo = articulo;
	}

	public String getNombre(){
		return articulo.getNombre();
	}

	public void setNombre(Articulo articulo){
		this.articulo.setNombre(articulo.getNombre());
	}

	public Double getTotal(){
		return total;
	}

	public void increaseQuantity(){
		this.qty++;
	}

	public void updatePrecio(){
		double resultado =this.qty * this.articulo.getPrecio();
		this.total =  Math.round(resultado * 100.0) / 100.0;
	}

	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		Ticket ticket = (Ticket) o;
		return Objects.equals(articulo, ticket.articulo);
	}

	@Override
	public int hashCode(){
		return Objects.hashCode(articulo);
	}

}
