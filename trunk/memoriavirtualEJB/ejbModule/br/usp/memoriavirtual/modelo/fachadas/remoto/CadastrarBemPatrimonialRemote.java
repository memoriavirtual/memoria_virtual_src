/**
 * 
 */
package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface CadastrarBemPatrimonialRemote {
	List<Instituicao> listarInstituicao(Usuario usuario)throws ModeloException;
	void cadastrarBemPatrimonial(BemPatrimonial bem)throws ModeloException;
	void salvarBemPatrimonial(BemPatrimonial bem)throws ModeloException;
	Instituicao recuperarInstituicao(String id) throws ModeloException;
	Autor recuperarAutor(String id) throws ModeloException;
}
