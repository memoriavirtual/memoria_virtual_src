package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface EnviarConviteRemote {

	public void enviarConvite(List<String> emails, String mensagem,
			String validade, boolean administrador, List<Acesso> acessos)
			throws ModeloException;

	public List<Instituicao> getInstituicoesPermitidas(Usuario usuario,
			Grupo grupo);

	public void enviarEmail(String destinatario, String assunto, String mensagem)
			throws ModeloException;
}
