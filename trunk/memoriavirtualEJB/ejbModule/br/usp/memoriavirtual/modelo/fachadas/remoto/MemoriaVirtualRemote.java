package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.io.IOException;
import java.net.InetAddress;

import javax.ejb.Remote;

@Remote
public interface MemoriaVirtualRemote {

	public InetAddress getEnderecoServidor() throws IOException;

	public boolean validarEmail(String email);
	public boolean disponibilidadeEmail(String email);
}
