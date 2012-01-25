package br.usp.memoriavirtual.modelo.fabricas;

import javax.ejb.Singleton;

import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fabricas.remoto.AuditoriaFabricaRemote;

@Singleton(mappedName = "AuditoriaFabrica")
public class AuditoriaFabrica implements AuditoriaFabricaRemote {

	@Override
	public void auditarExclusaoInstituicao(Usuario autorAcao,
			String atributoSignificativo) {
		// TODO Auto-generated method stub
		
	}

}
