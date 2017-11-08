package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.entidades.Autoria;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Assunto;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Descritor;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Titulo;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.VersionarBemPatrimonialRemote;

@Stateless(mappedName = "CadastrarBemPatrimonial")
public class CadastrarBemPatrimonial implements CadastrarBemPatrimonialRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@EJB
	private EditarBemPatrimonialRemote editarBemPatrimonialEJB;
	
	@EJB
	private VersionarBemPatrimonialRemote novaVersao;

	@Override
	public BemPatrimonial cadastrarBemPatrimonial(BemPatrimonial bem)
			throws ModeloException {
		for (Assunto a : bem.getAssuntos()) {
			Assunto assunto = entityManager.find(Assunto.class, a.getAssunto());
			if (assunto == null) {
				entityManager.persist(a);
			}
		}

		for (Descritor d : bem.getDescritores()) {
			Descritor descritor = entityManager.find(Descritor.class,
					d.getDescritor());
			if (descritor == null) {
				entityManager.persist(d);
			}
		}

		try {
			entityManager.persist(bem);

			for (Titulo t : bem.getTitulos()) {
				t.setBempatrimonial(bem);
			}

			for (Autoria a : bem.getAutorias()) {
				a.setBemPatrimonial(bem);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModeloException(e);
		}
		
		//cria entrada para esse bem na tabela de versoes
//		novaVersao.salvarVersaoBemPatrimonial(bem);
		
		return bem;
	}

	@Override
	public BemPatrimonial salvarBemPatrimonial(BemPatrimonial bem)
			throws ModeloException {

		return bem;
	}

	@Override
	public BemPatrimonial getBemPatrimonial(long id) throws ModeloException {
		try {
			return (BemPatrimonial) entityManager
					.find(BemPatrimonial.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModeloException(e);
		}
	}
}
