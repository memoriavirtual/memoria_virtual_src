package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;

@Remote
public interface EditarInstituicaoRemote {

	public String editarInstituicao(String velhoNome, String novoNome,
			String novoEmail, String novoLocalizacao, String novoEndereco,
			String novoCidade, String novoEstado, String novoCep,
			String novoTelefone);

	public List<Instituicao> getInstituicoes(Grupo grupo, Usuario usuario);

	public List<Instituicao> getInstituicoes();

	public List<Instituicao> getInstituicoesSugeridas(String pnome,
			Grupo grupo, Usuario usuario);

	public List<Instituicao> getInstituicoesSugeridas(String pnome);

}