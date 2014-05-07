package br.usp.memoriavirtual.modelo.fachadas.remoto;

import java.util.List;

import javax.ejb.Remote;

import br.usp.memoriavirtual.modelo.entidades.ContainerMultimidia;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.utils.MVModeloCamposMultimidia;

@Remote
public interface UtilMultimidiaRemote {

	public Long cadastrarMultimidia(Multimidia m) throws ModeloException;
	public void excluirMultimidia(long id) throws ModeloException;
	public Multimidia getMultimidia(long id) throws ModeloException;
	public String getContentType(long id) throws ModeloException;
	public void atualizarMidia(long id, String nome, String descricao) throws ModeloException;
	public List<MVModeloCamposMultimidia> listarCampos(ContainerMultimidia container) throws ModeloException; 

}
