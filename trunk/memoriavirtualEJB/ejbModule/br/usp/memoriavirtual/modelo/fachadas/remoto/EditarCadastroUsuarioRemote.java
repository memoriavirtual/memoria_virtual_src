package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface EditarCadastroUsuarioRemote {

	/**
	 * Obtem uma lista dos administradores do sistema
	 * 
	 * @param requrente
	 *            O usuario que faz a requisicao
	 * @return Lista de administradores do sistema (menos o usuario que fez a
	 *         requisicao, caso o mesmo seja administrador)
	 * @throws ModeloException
	 */
	public List<Usuario> listarAprovadores(Usuario usuario)
			throws ModeloException;

	/**
	 * Obtem uma lista dos acessos do usuario passado
	 * 
	 * @param usuario
	 *            Usuario cuja lista de acessos se deseja
	 * @return Lista dos acessos do usuario passado
	 * @throws ModeloException
	 */
	public List<Acesso> listarAcessos(Usuario usuario) throws ModeloException;

	/**
	 * Altera o nome e o telefone do usuario passado
	 * 
	 * @param usuario
	 *            Usuario cujas informacoes se deseja alterar
	 * @param nomeCompleto
	 *            Novo nome completo do usuario
	 * @param telefone
	 *            Novo telefone do usuario
	 * @throws ModeloException
	 */
	public void editarCadastro(Usuario usuario, String nomeCompleto,
			String telefone) throws ModeloException;

	/**
	 * Marca os acessos da lista passada no banco e envia um e-mail para o
	 * usuario aprovador
	 * 
	 * @param aprovador
	 *            Id do usuario que deve confirmar as alteracoes
	 * @param acessos
	 *            Lista de acessos para aprovacao
	 * @param data
	 *            Data das alteracoes
	 * @param expiracao
	 *            Data de expiracao das alteracoes
	 * @throws ModeloException
	 */
	public void editarAcessos(String aprovador, List<Acesso> acessos,
			List<String> situacoes, Date data, Date expiracao)
			throws ModeloException;

	/**
	 * Verifica se a operacao ainda esta na validade
	 * 
	 * @param aprovacao
	 *            Id da aprovacao a ser verificada
	 * @return true se a aprovacaoesta expirada e false c.c
	 * @throws ModeloException
	 */
	public boolean isAprovacaoExpirada(String aprovacao) throws ModeloException;

	/**
	 * Busca o acesso a ser aprovado
	 * 
	 * @param aprovacao
	 *            Id da aprovacao
	 * @return Acesso a ser aprovado
	 * @throws ModeloException
	 */
	public Acesso getAcesso(String aprovacao) throws ModeloException;

	public Aprovacao getAprovacao(String aprovacao) throws ModeloException;

	public void removerAprovacao(String aprovacao) throws ModeloException;

	public void remover(long id) throws ModeloException;

	public void persistir(long id) throws ModeloException;

	public List<Grupo> listarGrupos() throws ModeloException;
	public List<Instituicao> listarInstituicoes(String instituicao) throws ModeloException;
}
