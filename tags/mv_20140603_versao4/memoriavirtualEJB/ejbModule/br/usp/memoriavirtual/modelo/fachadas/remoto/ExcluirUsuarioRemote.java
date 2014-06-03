package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface ExcluirUsuarioRemote {
	public long solicitarExclusao(Usuario usuario, Aprovacao aprovacao) throws ModeloException;

	public void aprovar(Usuario usuario, Aprovacao aprovacao) throws ModeloException;

	public void negar(Usuario usuario, Aprovacao aprovacao)
			throws ModeloException;
}
