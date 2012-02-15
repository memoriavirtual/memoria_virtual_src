package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;
import javax.mail.MessagingException;

import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface MemoriaVirtualRemote {

	public String getURLServidor() throws ModeloException;
	public  String embaralhar (String mensagemOriginal);
	public List<Instituicao> listarInstituicoes(String pnome);
	public List<Instituicao> listarInstituicoes(String pnome,
			Grupo grupo, Usuario usuario);
	public boolean verificarDisponibilidadeIdUsuario(String id);
	public boolean verificarDisponibilidadeEmail(String email);
	public boolean verificarDisponibilidadeNomeInstituicao(String Nome);
	public void enviarEmail(String destinatario, String assunto, String mensagem) throws MessagingException;
	}
