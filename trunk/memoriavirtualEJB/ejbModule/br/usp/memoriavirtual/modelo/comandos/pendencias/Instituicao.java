package br.usp.memoriavirtual.modelo.comandos.pendencias;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.usp.memoriavirtual.modelo.comandos.Comando;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;


public class Instituicao implements Comando {

	Aprovacao aprovacao;

	public Instituicao(Aprovacao aprovacao) {
		this.aprovacao = aprovacao;
	}
	
	public void setAprovacao(Aprovacao aprovacao){
		this.aprovacao = aprovacao;
	}

	@Override
	public void executar() {
		
		ExcluirInstituicaoRemote excluirInstituicaoEJB = null;
		
		try {
			InitialContext ctx = new InitialContext();
			excluirInstituicaoEJB = (ExcluirInstituicaoRemote) ctx.lookup("ExcluirInstituicao");
		} catch (NamingException e) {
			e.printStackTrace();
			return;
		}
	
		try {
			br.usp.memoriavirtual.modelo.entidades.Instituicao inst = excluirInstituicaoEJB
					.recuperarInstituicaoFalse(aprovacao.getChaveEstrangeira());
			
			excluirInstituicaoEJB.marcarInstituicaoExcluida(inst, true, true);
			
			excluirInstituicaoEJB.excluirAprovacao(aprovacao);
			
		} catch (ModeloException e) {
			e.printStackTrace();
		}

	}

}
