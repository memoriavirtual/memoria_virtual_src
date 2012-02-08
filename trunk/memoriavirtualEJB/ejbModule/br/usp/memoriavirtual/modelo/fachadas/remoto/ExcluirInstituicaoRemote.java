package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;

@Remote
public interface ExcluirInstituicaoRemote{
	
	public List<Usuario> listarAdministradores() throws ModeloException;
	public List<Acesso> getGerentesdaInstituicao(Instituicao instituicao)throws ModeloException;
	public void enviaremail(String Email,String assunto,String textoEmail);
	public Aprovacao getAprovacao(String tabela,String chave)
	throws ModeloException;
	public Usuario getValidador(String nomeCompleto)
	throws ModeloException;
	public Usuario getRequisitor(String id)
	throws ModeloException;
	public void validarExclusaoInstituicao(Instituicao instituicao,Usuario requisitor,Usuario validador)
	throws ModeloException;
	public void registrarAprovacao(Usuario validador, Instituicao instituicao,
			Date dataValidade);
}