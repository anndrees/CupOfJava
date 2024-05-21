package model;

public enum TipoPedido {
	COMER_AQUI("Comer aquí"),
	PARA_LLEVAR("Para llevar"),
	DOMICILIO("Domicilio"),
	RECOGER("Para recoger");

	private final String textoFormateado;

	TipoPedido(String textoFormateado) {
		this.textoFormateado = textoFormateado;
	}

	@Override
	public String toString() {
		return textoFormateado;
	}
}
