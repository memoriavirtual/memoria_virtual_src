package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;

@ManagedBean(name = "editarCadastroUsuarioMB")
@SessionScoped
public class EditarCadastroUsuarioMB implements Serializable {

	private static final long serialVersionUID = -2609697377310497761L;
	@EJB
	private EditarCadastroUsuarioRemote editarCadastroUsuarioEJB;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	private String velhoNome;
	private String novoNome;
	private String novoTelefone;
	private List<Acesso> acesso;
	private List<Usuario> usuarios;

	public String editarCadastroUsuario() {

		if (this.validateId() && this.validateNome() && this.validateTelefone()) {

			try {

				this.editarCadastroUsuarioEJB.editarCadastroUsuario(
						this.velhoNome, this.novoNome, this.novoTelefone,
						this.acesso);
			} catch (ModeloException e) {
				MensagensDeErro.getErrorMessage(
						"editarInstituicaoErroEditarFalha", "resutado");
			} catch (RuntimeException e) {
				MensagensDeErro.getErrorMessage(
						"editarInstituicaoErroEditarFalha", "resultado");
			}
			this.velhoNome = "";
			this.novoNome = "";
			this.novoTelefone = "";
			this.acesso.clear();

			MensagensDeErro.getSucessMessage("editarInstituicaoSucessoEditar",
					"resultado");
		}
		return "falha";

	}

	public String cancelar() {
		return "cancelar";
	}

	public void usuariosSugeridos(AjaxBehaviorEvent event) {

		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		// Lista de instituicoes que o usuario pertence
		List<Acesso> usuariosLista = new ArrayList<Acesso>();

		if (usuario.isAdministrador()) {
			usuariosLista = this.editarCadastroUsuarioEJB
					.getUsuariosSugeridos(this.velhoNome);
		} else {
			Grupo grupo = new Grupo("Gerente");
			usuariosLista = this.editarCadastroUsuarioEJB.getUsuariosSugeridos(
					this.velhoNome, grupo, usuario);
		}

		for (Acesso a : usuariosLista)
			if (a.getUsuario().getNomeCompleto().equalsIgnoreCase(velhoNome))
				this.usuarios.add(a.getUsuario());
	}

	void validateId(AjaxBehaviorEvent e) {
		this.validateId();
	}

	boolean validateId() {
		if (this.velhoNome.equals("")) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroUsuario.erroIdVazio", "validacaoId");
			return false;
		} else if (memoriaVirtualEJB.disponibilidadeId(this.velhoNome)) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroUsuario.erroIdInvalida", "validacaoId");
			return false;
		}
		return true;
	}

	void validateNome(AjaxBehaviorEvent e) {
		this.validateNome();
	}

	boolean validateNome() {
		if (this.novoNome.equals("")) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroUsuario.erroNomeVazio", "validacaoNome");
			return false;
		} else if (!memoriaVirtualEJB.disponibilidadeId(this.velhoNome)
				&& !this.velhoNome.equals(this.novoNome)) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroUsuario.erroNomeInvalido", "validacaoNome");
			return false;
		}
		return true;
	}

	public void validateTelefone(AjaxBehaviorEvent event) {
		this.validateTelefone();
	}

	public boolean validateTelefone() {
		if (this.novoTelefone.equals("")) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroUsuario.telefoneVazio", "validacaoTelefone");
			return false;
		} else if (!ValidacoesDeCampos
				.validarFormatoTelefone(this.novoTelefone)) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroUsuario.telefoneFormatoIncorreto",
					"validacaoTelefone");
			return false;
		}
		
		return true;
	}

	/**
	 * @return editarCadastroUsuarioEJB
	 */
	public EditarCadastroUsuarioRemote getEditarCadastroUsuarioEJB() {
		return editarCadastroUsuarioEJB;
	}

	/**
	 * @param editarCadastroUsuarioEJB
	 *            , editarCadastroUsuarioEJB a ser configurado
	 */
	public void setEditarCadastroUsuarioEJB(
			EditarCadastroUsuarioRemote editarCadastroUsuarioEJB) {
		this.editarCadastroUsuarioEJB = editarCadastroUsuarioEJB;
	}

	/**
	 * @return memoriaVirtualEJB
	 */
	public MemoriaVirtualRemote getMemoriaVirtualEJB() {
		return memoriaVirtualEJB;
	}

	/**
	 * @param memoriaVirtualEJB
	 *            , memoriaVirtualEJB a ser configurado
	 */
	public void setMemoriaVirtualEJB(MemoriaVirtualRemote memoriaVirtualEJB) {
		this.memoriaVirtualEJB = memoriaVirtualEJB;
	}

	/**
	 * @return velhoNome
	 */
	public String getVelhoNome() {
		return velhoNome;
	}

	/**
	 * @param velhoNome
	 *            , velhoNome a ser configurado
	 */
	public void setVelhoNome(String velhoNome) {
		this.velhoNome = velhoNome;
	}

	/**
	 * @return novoNome
	 */
	public String getNovoNome() {
		return novoNome;
	}

	/**
	 * @param novoNome
	 *            , novoNome a ser configurado
	 */
	public void setNovoNome(String novoNome) {
		this.novoNome = novoNome;
	}

	/**
	 * @return novoTelefone
	 */
	public String getNovoTelefone() {
		return novoTelefone;
	}

	/**
	 * @param novoTelefone
	 *            , novoTelefone a ser configurado
	 */
	public void setNovoTelefone(String novoTelefone) {
		this.novoTelefone = novoTelefone;
	}

	/**
	 * @return acesso
	 */
	public List<Acesso> getAcesso() {
		return acesso;
	}

	/**
	 * @param acesso
	 *            , acesso a ser configurado
	 */
	public void setAcesso(List<Acesso> acesso) {
		this.acesso = acesso;
	}

	/**
	 * @return usuarios
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios
	 *            , usuarios a ser configurado
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}