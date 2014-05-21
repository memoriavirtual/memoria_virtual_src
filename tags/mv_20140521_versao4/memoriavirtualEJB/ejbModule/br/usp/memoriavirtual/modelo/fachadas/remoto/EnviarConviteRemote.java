package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface EnviarConviteRemote {
	
	public long enviarConvite(String[] emails, String mensagem,
			Date validade, boolean administrador, List<Acesso> acessos)
			throws ModeloException;

	public List<Instituicao> listarInstituicoes(Usuario usuario)
			throws ModeloException;

}
