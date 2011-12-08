package br.usp.memoriavirtual.modelo.fachadas.remoto;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

/**
 * Metodo para editar Instituicao, recebe a instituicao por parâmetro, altera e
 * salva no banco.
 * 
 * @param Instituicao
 *            A instituicao a ter seus dados alterados
 * @param String
 *            Nome da instituicao a ter os dados editados
 * @param String
 *            Localizacao da instituicao
 * @param String
 *            Endereco da instituicao
 * @param String
 *            Cidade da instituicao
 * @param String
 *            Estado da instituicao
 * @param String
 *            Cep da instituicao
 * @param String
 *            Telefone da instituicao
 * 
 * @throws ModeloException
 *             Caso a instituicao nao exista, ou nao possa ser alterada
 * 
 */
@Remote
public interface EditarInstituicaoRemote {

	public void editarInstituicao(Instituicao instituicao, String nome,
			String novoLocalizacao, String novoEndereco, String novoCidade,
			String novoEstado, String novoCep, String novoTelefone)
			throws ModeloException;

	public Instituicao getInstituicao(String pnome) throws ModeloException;

	public Instituicao getInstituicao(String pnome, Grupo grupo, Usuario usuario)
			throws ModeloException;

}