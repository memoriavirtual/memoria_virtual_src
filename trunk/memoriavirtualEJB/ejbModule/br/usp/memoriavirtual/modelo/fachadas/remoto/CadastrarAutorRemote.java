/**
 * 
 */
package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Autor;


/**
 * @author bigmac
 *
 */
@Remote
public interface CadastrarAutorRemote {
	public void cadastrarAutor (Autor autor);
}
