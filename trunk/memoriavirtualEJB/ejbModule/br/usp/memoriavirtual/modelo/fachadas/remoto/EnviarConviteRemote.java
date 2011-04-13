package br.usp.memoriavirtual.modelo.fachadas.remoto;
import java.util.ArrayList;

import javax.ejb.Remote;

@Remote
public interface EnviarConviteRemote {

	public String enviarConvite(ArrayList<String> emails, String validade, String nivelAcesso);	
}
