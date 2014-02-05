package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;
import javax.mail.MessagingException;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface MemoriaVirtualRemote {

	public static final Integer tamanhoPaginaDefault = 20;
	
	public Integer getTamanhoPagina();
	
	public String getURLServidor() throws ModeloException;

	public String getIntervaloTimer() throws ModeloException;

	public String embaralhar(String mensagemOriginal);

	public List<Acesso> listarAcessos(Usuario usuario);

	public List<Usuario> listarUsuarios(String pnome, Usuario usuario)
			throws ModeloException;

	public boolean verificarDisponibilidadeIdUsuario(String id);

	public boolean verificarDisponibilidadeEmail(String email);

	public boolean verificarDisponibilidadeNomeInstituicao(String Nome);

	public void enviarEmail(String destinatario, String assunto, String mensagem)
			throws MessagingException;
}
