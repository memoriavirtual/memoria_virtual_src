package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.Map;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface ValidacaoRemote {
	public boolean validacaoUnico(String query, Object o,
			Map<String, Object> parametros) throws ModeloException;

	public boolean validacaoNaoExiste(String query, Object o,
			Map<String, Object> parametros) throws ModeloException;
}
