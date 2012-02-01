package br.usp.memoriavirtual.modelo.fabricas;

import java.util.Date;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.entidades.ItemAuditoria;
import br.usp.memoriavirtual.modelo.entidades.EnumTipoAcao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fabricas.remoto.AuditoriaFabricaRemote;

@Singleton(mappedName = "AuditoriaFabrica")
public class AuditoriaFabrica implements AuditoriaFabricaRemote {

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	
	Date data = new Date();
	ItemAuditoria itemAuditoria = new ItemAuditoria();
	
	@Override
	public void auditarExcluirInstituicao(Usuario autorAcao,
			String atributoSignificativo) {
		itemAuditoria.setAtributoSignificativo(atributoSignificativo);
		itemAuditoria.setAutorAcao(autorAcao);
		itemAuditoria.setNotas("");
		itemAuditoria.setTipoAcao(EnumTipoAcao.EXCLUIR_INSTITUICAO);
		itemAuditoria.setData(data);
		entityManager.persist(itemAuditoria);
	}
	
	@Override
	public void auditarAutorizarExcluirInstituicao(Usuario autorAcao,
			String atributoSignificativo) {
		itemAuditoria.setAtributoSignificativo(atributoSignificativo);
		itemAuditoria.setAutorAcao(autorAcao);
		itemAuditoria.setNotas("");
		itemAuditoria.setTipoAcao(EnumTipoAcao.AUTORIZAR_EXCLUIR_INSTITUICAO);
		itemAuditoria.setData(data);
		entityManager.persist(itemAuditoria);
	}

}
