package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface EditarInstituicaoRemote {

	public void editarInstituicao(String velhoNome, String novoNome,
			String novoLocalizacao, String novoEndereco, String novoCidade,
			String novoEstado, String novoCep, String novoTelefone) throws ModeloException;

	public List<Instituicao> getInstituicoes(Grupo grupo, Usuario usuario);

	public List<Instituicao> getInstituicoes();

	public List<Instituicao> getInstituicoesSugeridas(String pnome,
			Grupo grupo, Usuario usuario);

	public List<Instituicao> getInstituicoesSugeridas(String pnome);

}