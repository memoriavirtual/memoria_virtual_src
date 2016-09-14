package br.usp.memoriavirtual.modelo.fachadas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EnviarConviteRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

@Stateless(mappedName = "EnviarConvite")
public class EnviarConvite implements EnviarConviteRemote {

	@EJB
	private MemoriaVirtualRemote memoriaVirtual;

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@Resource(name = "mail/memoriavirtual")
	private javax.mail.Session mailSession;

	public EnviarConvite() {

	}

	@Override
	public long enviarConvite(String[] emails, String mensagem, Date validade, boolean administrador,
			List<Acesso> acessos) throws ModeloException {
		try {
			for (String email : emails) {

				Usuario usuario = new Usuario();

				usuario.setValidade(validade);
				usuario.setEmail(email);
				usuario.setAtivo(false);
				usuario.setAdministrador(administrador);

				entityManager.persist(usuario);
				if (!administrador) {
					for (Acesso a : acessos) {
						Instituicao i = (Instituicao) entityManager.find(Instituicao.class, a.getInstituicao().getId());
						a.setInstituicao(i);
						Grupo g = entityManager.find(Grupo.class, a.getGrupo().getId());
						a.setGrupo(g);
						a.setUsuario(usuario);
						entityManager.persist(a);
					}
				}
				entityManager.flush();

				return usuario.getId();
			}
		} catch (Exception e) {
			throw new ModeloException(e);
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	public List<Instituicao> listarInstituicoes(Usuario usuario) throws ModeloException {

		try {
			Query query;
			List<Instituicao> instituicoes = new ArrayList<Instituicao>();
			if (usuario.isAdministrador()) {
				query = entityManager.createQuery("SELECT i FROM Instituicao i WHERE i.validade = true");
			} else {
				Grupo grupo = new Grupo("GERENTE");
				query = entityManager
						.createQuery("SELECT a.instituicao FROM Acesso a WHERE a.usuario = :usuario AND a.grupo = :grupo");
				query.setParameter("usuario", usuario);
				query.setParameter("grupo", grupo);
			}
			instituicoes = (List<Instituicao>) query.getResultList();
			return instituicoes;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}
}