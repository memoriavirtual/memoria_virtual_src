package br.usp.memoriavirtual.modelo.fabricas;

import java.util.Date;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.entidades.ItemAuditoria;
import br.usp.memoriavirtual.modelo.entidades.EnumTipoAcao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fabricas.remoto.AuditoriaFabricaRemote;

/**
 * @author MAC
 */


@Singleton(mappedName = "AuditoriaFabrica")
public class AuditoriaFabrica implements AuditoriaFabricaRemote {
	
	


	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	
	
	
	
	public void auditarExcluirInstituicao(Usuario autorAcao,
			String atributoSignificativo, String justificativa) {
		ItemAuditoria itemAuditoria = new ItemAuditoria();
		Date data = new Date();
		itemAuditoria.setAtributoSignificativo(atributoSignificativo);
		itemAuditoria.setAutorAcao(autorAcao);
		itemAuditoria.setNotas(justificativa);
		itemAuditoria.setTipoAcao(EnumTipoAcao.EXCLUIR_INSTITUICAO);
		itemAuditoria.setData(data);
		entityManager.persist(itemAuditoria);
	}
	
	
	public void auditarAutorizarExcluirInstituicao(Usuario autorAcao,
			String atributoSignificativo, String justificativa) {
		ItemAuditoria itemAuditoria = new ItemAuditoria();
		Date data = new Date();
		itemAuditoria.setAtributoSignificativo(atributoSignificativo);
		itemAuditoria.setAutorAcao(autorAcao);
		itemAuditoria.setNotas(justificativa);
		itemAuditoria.setTipoAcao(EnumTipoAcao.AUTORIZAR_EXCLUSAO);
		itemAuditoria.setData(data);
		entityManager.persist(itemAuditoria);
	}
	
	public void auditarNegarExcluirInstituicao(Usuario autorAcao,
			String atributoSignificativo, String justificativa) {
		ItemAuditoria itemAuditoria = new ItemAuditoria();
		Date data = new Date();
		itemAuditoria.setAtributoSignificativo(atributoSignificativo);
		itemAuditoria.setAutorAcao(autorAcao);
		itemAuditoria.setNotas(justificativa);
		itemAuditoria.setTipoAcao(EnumTipoAcao.NEGAR_EXCLUSAO);
		itemAuditoria.setData(data);
		entityManager.persist(itemAuditoria);
	}

}
