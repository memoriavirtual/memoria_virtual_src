package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Grupo;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;

@ManagedBean(name = "editarCadastroUsuarioMB")
@SessionScoped
public class EditarCadastroUsuarioMB implements Serializable {

	private static final long serialVersionUID = -2609697377310497761L;
	@EJB
	private EditarCadastroUsuarioRemote editarCadastroUsuarioEJB;
	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;
	private Acesso acesso;
	private String nome;
	private String nomeCompleto;
	private String telefone;
	private List<Usuario> usuarios;
	private List<Usuario> administradores;
	private Usuario usuario;
	private String administrador;
	private String justificativa;
	private Integer validade;
	private List<Acesso> acessos;
	private List<Acesso> acessosAntigos;
	private Aprovacao aprovacao;

	public EditarCadastroUsuarioMB() {
		super();
	}

	public String cancelar() {
		return "cancelar";
	}

	public void listarUsuarios(AjaxBehaviorEvent event) {
		this.listarUsuarios();
	}

	public String getInformacaoAcesso() {

		String chave[] = this.aprovacao.getChaveEstrangeira().split(";");

		return "Voce deseja " + chave[3] + " o seguinte acesso: Usuario: "
				+ this.acesso.getUsuario().getNomeCompleto() + " Instituicao "
				+ this.acesso.getInstituicao().getNome() + " Nivel: "
				+ this.acesso.getGrupo().getId() + " ?";

	}

	public void listarUsuarios() {

		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		if (usuario.isAdministrador()) {
			try {
				this.usuarios = this.editarCadastroUsuarioEJB
						.listarUsuarios(this.nome);
			} catch (ModeloException m) {
				MensagensDeErro.getErrorMessage(
						"editarCadastroUsuarioErroListarUsuarios", "resultado");
			}
		} else {
			Grupo grupo = new Grupo("gerente");
			try {
				this.usuarios = this.editarCadastroUsuarioEJB.listarUsuarios(
						this.nome, usuario, grupo);
			} catch (ModeloException m) {
				MensagensDeErro.getErrorMessage(
						"editarCadastroUsuarioErroListarUsuarios", "resultado");
			}
		}

	}

	public String selecionarUsuario(Usuario usuario) {

		Usuario requerente = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		this.usuario = usuario;
		this.nome = usuario.getNomeCompleto();
		this.telefone = usuario.getTelefone();
		try {
			this.acessos = this.editarCadastroUsuarioEJB.getAcessos(usuario);
			this.acessosAntigos = this.editarCadastroUsuarioEJB
					.getAcessos(usuario);
			this.administradores = this.editarCadastroUsuarioEJB
					.getAdministradores(requerente);
		} catch (ModeloException m) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroUsuarioErroSelecaoUsuario", "resultado");
			return "falha";
		}

		this.justificativa = null;
		this.usuarios.clear();
		this.setNome(null);
		this.nomeCompleto = usuario.getNomeCompleto();

		return "sucesso";
	}

	public String selecionarUsuario() {

		boolean existe = false;
		Usuario selecionado = null;
		Usuario requerente = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		this.listarUsuarios();

		for (Usuario u : this.usuarios) {
			if (u.getNomeCompleto().equals(this.nome)) {
				existe = true;
				selecionado = u;
			}
		}
		if (existe) {
			this.usuario = selecionado;
			this.telefone = selecionado.getTelefone();
			try {
				this.acessos = this.editarCadastroUsuarioEJB
						.getAcessos(selecionado);
				this.acessosAntigos = this.editarCadastroUsuarioEJB
						.getAcessos(selecionado);
				this.administradores = this.editarCadastroUsuarioEJB
						.getAdministradores(requerente);
			} catch (ModeloException m) {
				MensagensDeErro.getErrorMessage(
						"editarCadastroUsuarioErroSelecao", "resultado");
				return null;
			}

			this.justificativa = null;
			this.usuarios.clear();
			this.setNome(null);
			this.nomeCompleto = usuario.getNomeCompleto();
			return "sucesso";
		}
		MensagensDeErro.getErrorMessage("editarCadastroUsuarioUsuarioInvalido",
				"validacaoUsuario");
		return null;
	}

	public void validateNome(AjaxBehaviorEvent e) {
		this.validateNome();
	}

	public boolean validateNome() {
		if (this.nomeCompleto.equals("")) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroUsuarioErroNomeVazio", "validacaoNome");
			return false;
		} else if (this.nomeCompleto.length() < 4) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroUsuarioErroNomeCurto", "validacaoNome");
			return false;
		}
		return true;
	}

	public void validateTelefone(AjaxBehaviorEvent e) {
		this.validateTelefone();
	}

	public boolean validateTelefone() {
		if (!ValidacoesDeCampos.validarFormatoTelefone(this.telefone)) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroUsuarioErroTelefoneInvalido",
					"validacaoTelefone");
			return false;
		}
		return true;
	}

	public void validateJustificativa(AjaxBehaviorEvent e) {
		this.validateJustificativa();
	}

	public boolean validateJustificativa() {
		if (this.justificativa.equals("")) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroUsuarioErroJustificativaVazia",
					"validacaoJustificativa");
			return false;
		}
		return true;
	}

	public String excluirAcesso(Acesso acesso) {
		if (this.acessos.contains(acesso)) {
			this.acessos.remove(acesso);
		}
		return null;
	}

	public String adicionarAcesso() {

		Instituicao instituicao = new Instituicao();
		Grupo grupo = new Grupo();

		Acesso acesso = new Acesso();
		acesso.setGrupo(grupo);
		acesso.setInstituicao(instituicao);
		acesso.setUsuario(this.usuario);

		this.acessos.add(acesso);
		return null;
	}

	public String editarCadastroUsuario() {
		if (this.validateInstituicao() && this.validateJustificativa()
				&& this.validateNome() && this.validateTelefone()) {

			List<Acesso> pendentes = new ArrayList<Acesso>();
			List<String> situacoes = new ArrayList<String>();

			try {
				this.editarCadastroUsuarioEJB.editarCadastro(this.usuario,
						this.nomeCompleto, this.telefone);
			} catch (ModeloException m) {
				MensagensDeErro.getErrorMessage(
						"editarCadastroUsuarioErroEdicao", "resultado");
			}

			// adiciona a lista pendentes os acessos que foram modificados, ou
			// seja os acessos que estao em apenas uma das listas
			for (Acesso a : this.acessos) {
				for (Acesso o : this.acessosAntigos) {

					String nome1 = a.getInstituicao().getNome();
					String nome2 = o.getInstituicao().getNome();
					String id1 = a.getGrupo().getId();
					String id2 = o.getGrupo().getId();

					if (((nome1.equals(nome2)) && (id1.equals(id2)))) {
						pendentes.add(a);
						pendentes.add(o);
					}
				}
			}
			
			for (Acesso a : this.acessos) {
				System.out.println("atuais" + a.getInstituicao().getNome()
						+ a.getGrupo().getId());
			}

			for (Acesso a : this.acessosAntigos) {
				System.out.println("antigos" + a.getInstituicao().getNome()
						+ a.getGrupo().getId());
			}

			for (Acesso a : pendentes) {
				this.acessos.remove(a);
				this.acessosAntigos.remove(a);
			}

			for (Acesso a : pendentes) {
				System.out.println("pendentes" + a.getInstituicao().getNome()
						+ a.getGrupo().getId());
			}

			pendentes.clear();

			for (Acesso a : this.acessos) {
				pendentes.add(a);
				situacoes.add("adicionar");
			}

			for (Acesso a : this.acessosAntigos) {
				pendentes.add(a);
				situacoes.add("excluir");
			}

			for (Acesso a : this.acessos) {
				System.out.println("atuais" + a.getInstituicao().getNome()
						+ a.getGrupo().getId());
			}

			for (Acesso a : this.acessosAntigos) {
				System.out.println("antigos" + a.getInstituicao().getNome()
						+ a.getGrupo().getId());
			}

			Date data = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(data);
			calendar.add(Calendar.DAY_OF_MONTH, this.validade.intValue());
			Date expiracao = calendar.getTime();

			try {
				this.editarCadastroUsuarioEJB.editarAcessos(this.administrador,
						pendentes, situacoes, data, expiracao);
			} catch (Exception e) {
				MensagensDeErro.getErrorMessage(
						"editarCadastroUsuarioErroEdicao", "resultado");
			}

		}

		this.setNomeCompleto(null);
		this.setTelefone(null);
		this.setJustificativa(null);
		this.setAcessos(null);

		MensagensDeErro.getSucessMessage("editarCadastroUsuarioSucesso",
				"resultado");
		return "sucesso";
	}

	public List<SelectItem> getAdmnistradores() {

		List<SelectItem> adm = new ArrayList<SelectItem>();

		for (Usuario u : this.administradores) {
			adm.add(new SelectItem(u.getId(), u.getNomeCompleto()));
		}

		return adm;
	}

	public List<SelectItem> getValidades() {

		List<SelectItem> validades = new ArrayList<SelectItem>();

		validades.add(new SelectItem("1", "1 dia"));

		for (int i = 2; i <= 30; ++i) {
			validades.add(new SelectItem(i, i + " dias"));
		}

		return validades;
	}

	public void validateInstituicao(AjaxBehaviorEvent e) {
		this.validateInstituicao();
	}

	public boolean validateInstituicao() {
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");
		boolean existe = false;

		if (usuario.isAdministrador()) {
			for (Acesso a : this.acessos) {
				existe = this.memoriaVirtualEJB
						.verificarDisponibilidadeNomeInstituicao(a
								.getInstituicao().getNome());
				if (existe) {
					MensagensDeErro.getErrorMessage(
							"editarCadastroUsuarioErroInstituicaoInvalida",
							"resultado");
					return false;
				}
			}
		}

		return true;
	}

	public String confirmar() {

		String acao[] = this.aprovacao.getChaveEstrangeira().split(";");

		if (acao[3].equals("adicionar")) {
			try {
				this.editarCadastroUsuarioEJB.persistir(this.aprovacao.getId());
			} catch (ModeloException m) {
				m.printStackTrace();
			}
		} else if (acao[3].equals("excluir")) {
			try {
				this.editarCadastroUsuarioEJB.remover(this.aprovacao.getId());
			} catch (ModeloException m) {
				m.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * @return the editarCadastroUsuarioEJB
	 */
	public EditarCadastroUsuarioRemote getEditarCadastroUsuarioEJB() {
		return editarCadastroUsuarioEJB;
	}

	/**
	 * @param editarCadastroUsuarioEJB
	 *            the editarCadastroUsuarioEJB to set
	 */
	public void setEditarCadastroUsuarioEJB(
			EditarCadastroUsuarioRemote editarCadastroUsuarioEJB) {
		this.editarCadastroUsuarioEJB = editarCadastroUsuarioEJB;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone
	 *            the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the usuarios
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios
	 *            the usuarios to set
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * @return the administradores
	 */
	public List<Usuario> getAdministradores() {
		return administradores;
	}

	/**
	 * @param administradores
	 *            the administradores to set
	 */
	public void setAdministradores(List<Usuario> administradores) {
		this.administradores = administradores;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the administrador
	 */
	public String getAdministrador() {
		return administrador;
	}

	/**
	 * @param administrador
	 *            the administrador to set
	 */
	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}

	/**
	 * @return the acessos
	 */
	public List<Acesso> getAcessos() {
		return acessos;
	}

	/**
	 * @param acessos
	 *            the acessos to set
	 */
	public void setAcessos(List<Acesso> acessos) {
		this.acessos = acessos;
	}

	/**
	 * @return the justificativa
	 */
	public String getJustificativa() {
		return justificativa;
	}

	/**
	 * @param justificativa
	 *            the justificativa to set
	 */
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	/**
	 * @return the validade
	 */
	public Integer getValidade() {
		return validade;
	}

	/**
	 * @param validade
	 *            the validade to set
	 */
	public void setValidade(Integer validade) {
		this.validade = validade;
	}

	/**
	 * @return the memoriaVirtualEJB
	 */
	public MemoriaVirtualRemote getMemoriaVirtualEJB() {
		return memoriaVirtualEJB;
	}

	/**
	 * @param memoriaVirtualEJB
	 *            the memoriaVirtualEJB to set
	 */
	public void setMemoriaVirtualEJB(MemoriaVirtualRemote memoriaVirtualEJB) {
		this.memoriaVirtualEJB = memoriaVirtualEJB;
	}

	/**
	 * @return the nomeCompleto
	 */
	public String getNomeCompleto() {
		return nomeCompleto;
	}

	/**
	 * @param nomeCompleto
	 *            the nomeCompleto to set
	 */
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	/**
	 * @return the acessosAntigos
	 */
	public List<Acesso> getAcessosAntigos() {
		return acessosAntigos;
	}

	/**
	 * @param acessosAntigos
	 *            the acessosAntigos to set
	 */
	public void setAcessosAntigos(List<Acesso> acessosAntigos) {
		this.acessosAntigos = acessosAntigos;
	}

	public Acesso getAcesso() {
		return acesso;
	}

	public void setAcesso(Acesso acesso) {
		this.acesso = acesso;
	}

	public void setAcesso(String aprovacao) {
		try {
			this.acesso = this.editarCadastroUsuarioEJB.getAcesso(aprovacao);
		} catch (ModeloException m) {
			m.printStackTrace();
		}
	}

	public Aprovacao getAprovacao() {
		return aprovacao;
	}

	public void setAprovacao(Aprovacao aprovacao) {
		this.aprovacao = aprovacao;
	}

}