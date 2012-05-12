/**
 * 
 */
package br.usp.memoriavirtual.modelo.fachadas;

import java.util.List;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarAutorRemote;

/**
 * @author bigmac
 *
 */

@Stateless (mappedName = "EditarAutor")
public class EditarAutor implements EditarAutorRemote {
	
	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Autor> listarAutores (String strDeBusca) throws ModeloException {
		List<Autor> lista ;

		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM Autor a WHERE a.nome like :busca or a.sobrenome like :busca or a.codinome like :busca ORDER BY a.nome");
		query.setParameter("busca", strDeBusca+"%");
		try {
			lista = ( List<Autor> ) query.getResultList();
			return  lista ;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public void editarAutor(Autor autor) throws ModeloException {
		Autor autorAntigo;
	
		autorAntigo = this.entityManager.find(Autor.class,  autor.getId());
		
		if(autorAntigo != null){
			autorAntigo.setAtividade(autor.getAtividade());
			autorAntigo.setCodinome(autor.getCodinome());
			autorAntigo.setNascimento(autor.getNascimento());
			autorAntigo.setNome(autor.getNome());
			autorAntigo.setObito(autor.getObito());
			autorAntigo.setSobrenome(autor.getSobrenome());
			autorAntigo.setTipoAutoria(autor.getTipoAutoria());
		}
		
		try {
			entityManager.flush();
		} catch (Exception t) {
			throw new ModeloException(t);
		}
	}
}
