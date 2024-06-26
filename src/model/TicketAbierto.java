package model;

import java.util.List;
import java.util.Objects;

public class TicketAbierto{

	String nombre;
	TipoPedido tipo;
	double total;

	public TicketAbierto(String nombre, TipoPedido tipo, List<Ticket> ticketsAbiertos){
		this.nombre = nombre;
		this.tipo = tipo;

		for (Ticket ticket : ticketsAbiertos) {
			total += ticket.getTotal();
		}
	}

	public String getNombre(){
		return nombre;
	}

	public TipoPedido getTipoPedido(){
		return tipo;
	}

	public double getTotal(){
		return total;
	}

	public void setTotal(double total){
		this.total = total;
	}

	public void setTipo(TipoPedido tipo){
		this.tipo = tipo;
	}

	public void setNombre(String nombre){
		this.nombre = nombre;
	}

	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		TicketAbierto that = (TicketAbierto) o;
		return Objects.equals(nombre, that.nombre);
	}

	@Override
	public int hashCode(){
		return Objects.hashCode(nombre);
	}
}
