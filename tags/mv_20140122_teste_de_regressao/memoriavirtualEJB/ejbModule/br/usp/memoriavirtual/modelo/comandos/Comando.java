package br.usp.memoriavirtual.modelo.comandos;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;

public interface Comando {

	public void executar();
	public void setAprovacao(Aprovacao aprovacao);
	
}
