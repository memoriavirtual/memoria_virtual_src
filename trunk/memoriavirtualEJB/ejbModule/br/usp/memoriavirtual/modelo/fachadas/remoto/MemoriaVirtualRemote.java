package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.io.IOException;
import java.net.InetAddress;

import javax.ejb.Remote;
import javax.mail.MessagingException;

@Remote
public interface MemoriaVirtualRemote {

	public InetAddress getEnderecoServidor() throws IOException;

	public boolean disponibilidadeId(String id);
	public boolean validarEmail(String email);
	public boolean disponibilidadeEmail(String email);
	public void enviarEmail(String destinatario, String assunto, String mensagem) throws MessagingException;
}
