package br.usp.memoriavirtual.modelo.fabricas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Usuario;

@Remote
public interface AuditoriaFabricaRemote {
	/**
	 * Método insere uma linha na tabela ItemAuditoria, referente a o pedido
	 * de exclusão da instituição
	 * @param autorAcao
	 * @param atributoSignificativo
	 */
	public void auditarExcluirInstituicao(Usuario autorAcao,String atributoSignificativo,String justificativa);
	/**
	 * Método insere uma linha na tabela ItemAuditoria, referente a a autorização
	 * de um pedido de exclusão da instituição
	 * @param autorAcao
	 * @param atributoSignificativo
	 */
	public void auditarAutorizarExcluirInstituicao(Usuario autorAcao,String atributoSignificativo);
}
