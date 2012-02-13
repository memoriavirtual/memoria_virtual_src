package br.usp.memoriavirtual.modelo.fabricas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Usuario;


/**
 * @author MAC
 */

@Remote
public interface AuditoriaFabricaRemote {
	
	
	
	
	/**
	 * M�todo insere uma linha na tabela ItemAuditoria, referente a o pedido
	 * de exclus�o da institui��o
	 * @param autorAcao
	 * @param atributoSignificativo
	 */
	public void auditarExcluirInstituicao(Usuario autorAcao,String atributoSignificativo,String justificativa);
	/**
	 * M�todo insere uma linha na tabela ItemAuditoria, referente a a autoriza��o
	 * de um pedido de exclus�o da institui��o
	 * @param autorAcao
	 * @param atributoSignificativo
	 * @param justificativa 
	 */
	public void auditarAutorizarExcluirInstituicao(Usuario autorAcao,String atributoSignificativo, String justificativa);
}
