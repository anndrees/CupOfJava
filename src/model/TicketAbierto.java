package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TicketAbierto{

	String nombre;
	TipoPedido tipo;
	private List<Ticket> ticketsAbiertos;
	double total;

	public TicketAbierto(String nombre, TipoPedido tipo, List<Ticket> ticketsAbiertos){
		this.nombre = nombre;
		this.tipo = tipo;
		this.ticketsAbiertos = ticketsAbiertos;

		for (Ticket ticket : ticketsAbiertos) {
			total += ticket.getTotal();
		}
	}

	public String getNombre(){
		return nombre;
	}

	public String getTipo(){
		return tipo.toString();
	}

	public TipoPedido getTipoPedido(){
		return tipo;
	}

	public List<Ticket> getTicketsAbiertos(){
		return ticketsAbiertos;
	}

	public double getTotal(){
		return total;
	}

	public void setTotal(double total){
		this.total = total;
	}

	public void setTicketsAbiertos(List<Ticket> ticketsAbiertos){
		this.ticketsAbiertos = ticketsAbiertos;
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
