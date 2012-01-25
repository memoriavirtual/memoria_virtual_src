package br.usp.memoriavirtual.modelo.fabricas;

import javax.ejb.Singleton;

import br.usp.memoriavirtual.modelo.fabricas.remoto.AuditoriaFabricaRemote;

@Singleton(mappedName = "AuditoriaFabrica")
public class AuditoriaFabrica implements AuditoriaFabricaRemote {

}
