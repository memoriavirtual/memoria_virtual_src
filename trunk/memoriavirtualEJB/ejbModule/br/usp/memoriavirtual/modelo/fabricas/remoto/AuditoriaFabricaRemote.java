package br.usp.memoriavirtual.modelo.fabricas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Usuario;

@Remote
public interface AuditoriaFabricaRemote {
	/**
	 * 
	 * @param autorAcao
	 * @param atributoSignificativo
	 */
	public void auditarExclusaoInstituicao(Usuario autorAcao,String atributoSignificativo);
}
