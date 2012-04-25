package br.usp.memoriavirtual.modelo.fabricas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Usuario;


/**
 * @author MAC
 */

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
	 * @param justificativa 
	 */
	public void auditarAutorizarExcluirInstituicao(Usuario autorAcao,String atributoSignificativo, String justificativa);
	/**
	 * Método insere uma linha na tabela ItemAuditoria, referente a a autorização
	 * de um pedido de exclusão da instituição
	 * @param autorAcao
	 * @param atributoSignificativo
	 * @param justificativa 
	 */
	public void auditarNegarExcluirInstituicao(Usuario autorAcao,String atributoSignificativo, String justificativa);

	public void auditarExcluirUsuario(Usuario autorAcao,String atributoSignificativo,String justificativa);
	public void auditarAutorizarExcluirUsuario(Usuario autorAcao,String atributoSignificativo, String justificativa);
	public void auditarNegarExcluirUsuario(Usuario autorAcao,String atributoSignificativo, String justificativa);

}
