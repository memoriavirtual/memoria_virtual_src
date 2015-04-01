package br.usp.memoriavirtual.utils;

public enum BibliotecasParaBuscar {
	SELECT2;

	@Override
	public String toString() {
		switch (this) {
		case SELECT2:
			return "select2";

		default:
			break;
		}

		return null;
	}
}
