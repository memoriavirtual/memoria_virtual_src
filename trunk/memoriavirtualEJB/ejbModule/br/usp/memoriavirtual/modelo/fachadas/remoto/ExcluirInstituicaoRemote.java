package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;


import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface ExcluirInstituicaoRemote{
	/**
	 * Metodo auxiliar para recuperar usuario ligado a determinada instituição
	 * 
	 * @param instituicao
	 *            instituicao
	 * @param grupo
	 *            Grupo ao qual o usuario pertence
	 * @return Usuario pertencente a referido grupo vinculado a referida instituição
	 * @throws ModeloException
	 *             Em caso de erro
	 */
	public List<Usuario> listarAdministradores() throws ModeloException;
	public Usuario getGerentesdaInstituicao(Instituicao instituicao)throws ModeloException;
	public String enviaremail(String Email,String assunto,String textoEmail);
	public Usuario getValidador(String nomeCompleto)
	throws ModeloException;
	public Usuario getRequisitor(String id)
	throws ModeloException;
	public void excluirInstituicao(String nome,Usuario requisitor,Usuario validador)
	throws ModeloException;
}