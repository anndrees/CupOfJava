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
		return switch(tipo.toLowerCase()){
			case "comer aquí", "comer aqui" -> AQUI;
			case "para llevar" -> LLEVAR;
			case "domicilio" -> DOMICILIO;
			case "para recoger" -> RECOGER;
			default -> null;
		};
	}

}
