package br.usp.memoriavirtual.controle;

import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.Multimidia;

public interface BeanComMidia {
	public List<Multimidia> recuperaColecaoMidia();
	public void adicionarMidia (Multimidia midia);
}
