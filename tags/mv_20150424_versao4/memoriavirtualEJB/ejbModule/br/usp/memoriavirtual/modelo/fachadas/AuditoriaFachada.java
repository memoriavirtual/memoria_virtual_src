package br.usp.memoriavirtual.modelo.fachadas;

import javax.ejb.Stateless;

import br.usp.memoriavirtual.modelo.fachadas.remoto.AuditoriaFachadaRemote;

@Stateless (mappedName = "AuditoriaFachada")
public class AuditoriaFachada implements AuditoriaFachadaRemote {

}
