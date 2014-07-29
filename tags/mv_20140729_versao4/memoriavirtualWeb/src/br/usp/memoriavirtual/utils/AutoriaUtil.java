package br.usp.memoriavirtual.utils;

import br.usp.memoriavirtual.modelo.entidades.Autoria;

public class AutoriaUtil {

	private String autor = "";
	private Autoria.TipoAutoria tipo;

	public AutoriaUtil() {

	}

	public AutoriaUtil(String autor, Autoria.TipoAutoria tipo) {
		this.autor = autor;
		this.tipo = tipo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Autoria.TipoAutoria getTipo() {
		return tipo;
	}

	public void setTipo(Autoria.TipoAutoria tipo) {
		this.tipo = tipo;
	}
}
