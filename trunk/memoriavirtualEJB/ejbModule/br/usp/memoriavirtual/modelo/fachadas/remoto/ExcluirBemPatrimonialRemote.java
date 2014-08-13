package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface ExcluirBemPatrimonialRemote {

	public List<Usuario> listarUsuariosAprovadores(Instituicao instituicao, Usuario usuario) throws ModeloException;

	public void excluir(long id) throws ModeloException;

}
