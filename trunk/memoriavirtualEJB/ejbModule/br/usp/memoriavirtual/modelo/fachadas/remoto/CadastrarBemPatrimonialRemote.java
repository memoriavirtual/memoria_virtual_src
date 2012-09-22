/**
 * 
 */
package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

/**
 * @author bigmac
 *
 */
public interface CadastrarBemPatrimonialRemote {
	List<Instituicao> listarInstituicao(Usuario usuario)throws ModeloException;
	void cadastrarBemPatrimonial(BemPatrimonial bem);
}
