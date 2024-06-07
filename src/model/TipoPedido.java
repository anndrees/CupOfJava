package model;

public enum TipoPedido {
	AQUI("Comer aquí"),
	LLEVAR("Para llevar"),
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

	public static TipoPedido fromString(String tipo) {
		switch(tipo.toLowerCase()) {
			case "comer aquí":
				return AQUI;
			case "comer aqui":
				return AQUI;
			case "para llevar":
				return LLEVAR;
			case "domicilio":
				return DOMICILIO;
			case "para recoger":
				return RECOGER;
			default:
				return null;
		}
	}

}
