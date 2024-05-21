package model;

public enum Categorias{
	TODOS("Todos los artículos"),
	REFRESCOS("Refrescos"),
	POSTRES("Postres"),
	CAFES("Cafés"),
	OTROS("Otros");

	private final String textoFormateado;

	Categorias(String textoFormateado) {
		this.textoFormateado = textoFormateado;
	}

	@Override
	public String toString() {
		return textoFormateado;
	}
}
