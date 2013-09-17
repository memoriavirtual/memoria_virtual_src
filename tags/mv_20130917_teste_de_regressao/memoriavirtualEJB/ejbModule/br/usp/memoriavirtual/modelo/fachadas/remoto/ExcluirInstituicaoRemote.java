package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.ItemAuditoria;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;


/**
 * @author MAC
 */
@Remote
public interface ExcluirInstituicaoRemote{
	
	public List<Instituicao> listarTodasInstituicoes(Grupo grupo, Usuario usuario) throws ModeloException;
	/**
	 * Métodos faz uma requisição no banco de dados afim de encontrar todos 
	 * os usuários do memória virtual que são administradores.
	 * @return
	 * Lista de usuários que são administradores.
	 * @throws ModeloException
	 */
	public List<Usuario> listarAdministradores() throws ModeloException;
	
	
	
	/**
	 * Método faz uma requisição no banco de dados afim de encontrar uma instituição
	 * cujo nome corresponda a String que é recebida como parâmetro e que não esteja mais ativa no 
	 * sistema do memória virtual.
	 * @param nomeInstituicao
	 * Parâmetro útil para encontrar a instituição desejada.
	 * @return
	 * Retorna Objeto Instituicao cujo nome coincide com o parâmetro passado.
	 * @throws ModeloException
	 */
	public Instituicao recuperarInstituicaoFalse(String nomeInstituicao) throws ModeloException ;
	
	
	/**
	 * Método faz uma requisição no banco de dados afim de encontrar um objeto ItemAuditoria
	 * @return
	 * Retorna 
	 * Objeto ItemAuditoria.
	 * @throws ModeloException
	 */
	public ItemAuditoria recuperarItemAuditoria(long chave) throws ModeloException;
	
	
	/**
	 * Método faz uma requisição no banco de dados afim de encontrar uma lista de objetos Acesso
	 * cujo a coluna instituicao corresponde ao parâmetro instituicao, e que a coluna validade 
	 * corresponde ao parâmetro validade.
	 * @param 
	 * instituicao
	 * @param 
	 * validade
	 * @return
	 * Objeto List<Acesso>
	 * @throws ModeloException
	 */
	public List<Usuario> recuperarGerentesdaInstituicao(Instituicao instituicao, boolean validade)throws ModeloException;
	
	
	/**
	 * Envia o email 
	 * @param Email
	 * @param assunto
	 * @param textoEmail
	 */
	public void enviaremail(String Email,String assunto,String textoEmail);
	
	
	/**
	 * Método faz uma requisição no banco de dados afim de encontrar um objeto Aprovação
	 * cujo a coluna chaveEstrangeira corresponde ao parâmetro chave, e que a coluna tabelaEstrangeira 
	 * corresponde a Instituicao.
	 * @param chave
	 * @return
	 * Objeto Aprovacao
	 * @throws ModeloException
	 */
	public Aprovacao recuperarAprovacao(long chave)
	throws ModeloException;
	
	
	/**
	 * Método faz uma requisição no banco de dados afim de encontrar um objeto Usuario
	 * @param coluna
	 * @return
	 * Objeto Usuario
	 * @throws ModeloException
	 */
	public Usuario getUsuario(String coluna, String parametro)
	throws ModeloException;
	/**
	 * Método efetiva a exclusão da instituição ou reativa a instituição, dependendo do estado do flag acao
	 * ele também trata das relações com o objeto acesso. 
	 * @param instituicao
	 * @param acao
	 * @param flagAcesso
	 * @throws ModeloException
	 */
	public void validarExclusaoInstituicao(Instituicao instituicao, boolean acao,boolean flagAcesso)
	throws ModeloException;
	/**
	 * Registra um objeto Aprovação no banco de dados
	 * @param validador
	 * @param instituicao
	 * @param dataValidade
	 * @return 
	 */
	public Aprovacao registrarAprovacao(Usuario validador, Instituicao instituicao,
			Date dataValidade);
	/**
	 * Método marca uma instituição que deve ser aprovada para ser excluída
	 * @param instituicao
	 * @param acao
	 * @param flagAcesso
	 * @throws ModeloException
	 */
	public void marcarInstituicaoExcluida(Instituicao instituicao, boolean acao,boolean flagAcesso)
	throws ModeloException;
	public void excluirAprovacao(Aprovacao aprovacao);
	List<Instituicao> listarTodasInstituicoes() throws ModeloException;
	
}