package br.usp.memoriavirtual.modelo.comandos;

import java.util.logging.Logger;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;

public class Instituicao implements Comando{

	Aprovacao aprovacao;
	
	private Logger logger = Logger
			.getLogger("br.usp.memoriavirtual.modelo.comandos.Instituicao");
	
	public Instituicao(Aprovacao aprovacao){
		this.aprovacao = aprovacao;
	}
	
	@Override
	public void executar() {
		logger.info("Passei na aprovacao");
		
	}

}
