package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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
	private Acesso acesso; // acesso do
	private String nome = ""; // nome a ser buscado para oferecer sugestoes
	private String nomeCompleto; // nome a ser exibido na visão de edição
	private String telefone;
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private List<Usuario> administradores;
	private List<Instituicao> instituicoes;
	private Usuario usuario;
	private String administrador;
	private String justificativa;
	private String instituicao;
	private Integer validade;
	private List<Acesso> acessos;
	private List<Acesso> acessosAntigos;
	private Aprovacao aprovacao;
	private boolean exibirAcessos = false; // exibir a tabela de acessos
	private boolean mostrarLink = true; // renderizar link para exibir a tabela
										// de acessos
	private boolean renderInstituicoes = false;
	private boolean marcadoAdministrador;

	public EditarCadastroUsuarioMB() {
		super();
	}

	public void listarUsuariosFocus(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		this.usuarios.clear();
		Usuario ins = new Usuario();
		ins.setNomeCompleto(bundle.getString("listarTodos"));
		this.usuarios.add(0, ins);
	}

	public void listarUsuarios() {

		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		if (usuario.isAdministrador()) {

			try {

				this.usuarios = this.memoriaVirtualEJB.listarUsuarios(
						this.nome, usuario);
				if (this.usuarios.contains(usuario))
					this.usuarios.remove(usuario);

			} catch (ModeloException m) {
				MensagensDeErro.getErrorMessage(
						"editarCadastroUsuarioErroListarUsuarios", "resultado");
			}
		}

	}

	public String getInformacaoAcesso() {

		String chave[] = this.aprovacao.getChaveEstrangeira().split(";");

		if (chave.length == 5) {

			return "Voce deseja " + chave[3] + " o seguinte acesso: Usuario: "
					+ this.acesso.getUsuario().getNomeCompleto()
					+ " Instituicao: " + this.acesso.getInstituicao().getNome()
					+ " Nivel: " + this.acesso.getGrupo().getId() + " ?";
		} else {
			String acao;

			try {
				this.usuario = this.editarCadastroUsuarioEJB
						.getUsuario(chave[2]);
			} catch (ModeloException m) {
				MensagensDeErro.getErrorMessage("erro", "resultado");
				m.printStackTrace();
				return null;
			}

			if (chave[0].equals(new Boolean(true).toString()))
				acao = "adicionar";
			else
				acao = "remover";

			return "Voce deseja " + acao + " privilegios de administrador?\n"
					+ "Usuario: " + this.usuario.getNomeCompleto() + "\n";
		}

	}

	public String getInformacaoJustificativa() {

		String chave[] = this.aprovacao.getChaveEstrangeira().split(";");

		if (chave.length == 5)
			return "Justificativa: " + chave[4];
		else
			return "Justificativa: " + chave[1];

	}

	public String cancelar() {
		this.limpar();
		return "cancelar";
	}

	public String mostrar() {
		this.exibirAcessos = true;
		this.mostrarLink = false;
		return null;
	}

	/**
	 * Método a ser chamado quando o usuário clica em um dos nomes da lista
	 * 
	 * @param usuario
	 *            Usuario a ser editado
	 * @return
	 */
	public String selecionarUsuario(Usuario usuario) {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		// pega o usuário requerente
		Usuario requerente = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		// se for a opção para listar todos os usuários, refaz a busca no
		// banco
		if (usuario.getNomeCompleto().equals(bundle.getString("listarTodos"))) {
			this.nome = "";
			this.listarUsuarios();
			this.usuarios.remove(0);
			return null;
		}

		// usuario a ser editado
		this.usuario = usuario;

		// nome do usuario a ser editado
		this.nome = usuario.getNomeCompleto();

		// telefone do usuario a ser editado
		this.telefone = usuario.getTelefone();

		// tenta buscar os acessos para um usuario e uma lista de aprovadores
		// validos
		try {
			this.acessos = this.editarCadastroUsuarioEJB.listarAcessos(usuario);
			this.acessosAntigos = this.editarCadastroUsuarioEJB
					.listarAcessos(usuario);
			this.administradores = this.editarCadastroUsuarioEJB
					.listarAprovadores(requerente, this.usuario);
		} catch (ModeloException m) {
			MensagensDeErro.getErrorMessage(
					"editarCadastroUsuarioErroSelecaoUsuario", "resultado");
			return "falha";
		}

		// limpa os dados da sessão
		this.justificativa = null;
		this.usuarios.clear();
		this.nome = null;
		this.nomeCompleto = usuario.getNomeCompleto();
		this.marcadoAdministrador = this.usuario.isAdministrador();

		return "sucesso";
	}

	public String selecionarUsuario() {

		boolean existeUsuario = false;
		Usuario selecionado = null;
		Usuario requerente = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		this.listarUsuarios();
		if (this.nome == "") {
			MensagensDeErro.getErrorMessage("excluirAutorErro", "resultado");
			return null;
		}
		for (Usuario u : this.usuarios) {
			if (u.getNomeCompleto().equals(this.nome)) {
				existeUsuario = true;
				selecionado = u;
			}
		}
		if (existeUsuario) {
			this.usuario = selecionado;
			this.telefone = selecionado.getTelefone();
			try {
				this.acessos = this.editarCadastroUsuarioEJB
						.listarAcessos(selecionado);
				this.acessosAntigos = this.editarCadastroUsuarioEJB
						.listarAcessos(selecionado);
				this.administradores = this.editarCadastroUsuarioEJB
						.listarAprovadores(requerente, this.usuario);
			} catch (ModeloException m) {
				MensagensDeErro.getErrorMessage(
						"editarCadastroUsuarioErroSelecao", "resultado");
				return null;
			}

			this.justificativa = null;
			this.usuarios.clear();
			this.nome = null;
			this.nomeCompleto = usuario.getNomeCompleto();
			this.marcadoAdministrador = this.usuario.isAdministrador();
			return "sucesso";
		}

		MensagensDeErro.getErrorMessage(
				"editarCadastroUsuarioErroUsuarioInvalido", "resultado");

		return null;
	}

	public String selecionarInstituicao(Acesso acesso, Instituicao i) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		if (i.getNome().equals(bundle.getString("listarTodos"))) {
			this.listarInstituicoes("");
			return null;
		}

		if (acesso != null && i != null) {
			if (this.acessos.contains(acesso) && this.instituicoes.contains(i))
				this.acessos.get(this.acessos.indexOf(acesso))
						.setInstituicao(i);
			this.instituicoes.clear();
			return null;
		} else {
			return null;
		}

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

	public void validateAprovador(AjaxBehaviorEvent e) {
		this.validateTelefone();
	}

	public boolean validateAprovador() {

		if (this.administrador == null) {
			MensagensDeErro
					.getErrorMessage("editarCadastroUsuarioAprovadorVazio",
							"validacaoAprovador");
			return false;
		}

		return true;
	}

	public void validateTelefone(AjaxBehaviorEvent e) {
		this.validateTelefone();
	}

	public boolean validateTelefone() {
		if (this.telefone.equals("")) {
			MensagensDeErro.getErrorMessage(
					"cadastrarInstituicaoErroTelefoneVazio",
					"validacaoTelefone");
			return false;
		}
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

		if (this.instituicoes == null)
			this.instituicoes = new ArrayList<Instituicao>();

		Instituicao instituicao = new Instituicao();
		Grupo grupo = new Grupo();

		Acesso acesso = new Acesso();
		acesso.setGrupo(grupo);
		acesso.setInstituicao(instituicao);
		acesso.setUsuario(this.usuario);
		this.instituicoes.clear();

		this.acessos.add(acesso);
		return null;
	}

	public String editarCadastroUsuario() {

		this.validateNome();
		this.validateTelefone();

		if (this.exibirAcessos)
			this.validateAprovador();

		if (!FacesContext.getCurrentInstance().getMessages().hasNext()) {

			List<Acesso> pendentes = new ArrayList<Acesso>();
			List<String> situacoes = new ArrayList<String>();

			try {
				this.editarCadastroUsuarioEJB.editarCadastro(this.usuario,
						this.nomeCompleto, this.telefone);
				if (exibirAcessos == false) {
					MensagensDeErro.getSucessMessage(
							"editarCadastroUsuarioSucesso", "resultado");
					return null;
				}
			} catch (ModeloException m) {
				MensagensDeErro.getErrorMessage(
						"editarCadastroUsuarioErroEdicao", "resultado");
				m.printStackTrace();
				if (exibirAcessos == false)
					return null;
			}

			if (exibirAcessos && validateInstituicao()
					&& validateJustificativa()) {

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

				for (Acesso a : pendentes) {
					this.acessos.remove(a);
					this.acessosAntigos.remove(a);
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

				Date data = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(data);
				calendar.add(Calendar.DAY_OF_MONTH, this.validade.intValue());
				Date expiracao = calendar.getTime();

				try {
					this.editarCadastroUsuarioEJB.editarAcessos(this.usuario,
							this.administrador, pendentes, situacoes, data,
							expiracao, this.justificativa,
							this.marcadoAdministrador);
					this.limpar();
					MensagensDeErro.getSucessMessage(
							"editarCadastroUsuarioSucesso", "resultado");
				} catch (Exception e) {
					this.limpar();
					MensagensDeErro.getErrorMessage(
							"editarCadastroUsuarioErroEdicao", "resultado");
					e.printStackTrace();
				}

			}
		}
		this.limpar();
		return null;
	}

	public List<Instituicao> listarInstituicoes(String instituicao) {

		if (this.instituicoes.isEmpty()) {
			FacesContext context = FacesContext.getCurrentInstance();
			String bundleName = "mensagens";
			ResourceBundle bundle = context.getApplication().getResourceBundle(
					context, bundleName);

			Instituicao i = new Instituicao();
			i.setNome(bundle.getString("listarTodos"));
			this.instituicoes.add(i);
		} else {
			try {
				this.instituicoes = editarCadastroUsuarioEJB
						.listarInstituicoes(instituicao);
				if (this.instituicoes.size() > 0)
					this.renderInstituicoes = true;
				else
					this.renderInstituicoes = false;
			} catch (ModeloException m) {
				m.printStackTrace();
			}
		}

		return this.instituicoes;
	}

	public List<SelectItem> getNivel() {

		List<SelectItem> niveis = new ArrayList<SelectItem>();
		List<Grupo> grupos = new ArrayList<Grupo>();

		try {
			grupos = this.editarCadastroUsuarioEJB.listarGrupos();
		} catch (ModeloException m) {
			m.printStackTrace();
		}

		for (Grupo g : grupos) {
			niveis.add(new SelectItem(g.getId(), g.getId()));
		}

		return niveis;

	}

	public List<SelectItem> getAdmnistradores() {

		List<SelectItem> administradores = new ArrayList<SelectItem>();

		for (Usuario u : this.administradores) {
			administradores.add(new SelectItem(u.getId(), u.getNomeCompleto()));
		}

		return administradores;
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
		if (exibirAcessos) {
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
		} else {
			return true;
		}
	}

	public String confirmar() {

		Usuario requerente = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		Usuario aprovador = this.aprovacao.getAprovador();

		if (aprovador.getId().equals(requerente.getId())) {

			String acao[] = this.aprovacao.getChaveEstrangeira().split(";");

			if (acao.length == 3) {

				Boolean administrador = new Boolean(acao[0]);

				this.usuario.setAdministrador(administrador.booleanValue());

				try {
					this.editarCadastroUsuarioEJB.removerAprovacao(aprovacao
							.getId().toString());
					this.editarCadastroUsuarioEJB.merge(this.usuario);
					MensagensDeErro.getSucessMessage(
							"editarCadastroUsuarioSucesso", "resultado");
					return null;
				} catch (ModeloException m) {
					MensagensDeErro
							.getErrorMessage(
									"editarCadastroUsuarioErroConfirmacao",
									"resultado");
					m.printStackTrace();
					return null;
				}

			}

			if (acao[3].equals("adicionar")) {
				try {
					this.editarCadastroUsuarioEJB.persistir(this.aprovacao
							.getId());
					MensagensDeErro.getSucessMessage(
							"editarCadastroUsuarioSucesso", "resultado");
				} catch (ModeloException m) {
					MensagensDeErro
							.getErrorMessage(
									"editarCadastroUsuarioErroConfirmacao",
									"resultado");
					m.printStackTrace();
				}
			} else if (acao[3].equals("excluir")) {
				try {
					this.editarCadastroUsuarioEJB.remover(this.aprovacao
							.getId());
					MensagensDeErro.getSucessMessage(
							"editarCadastroUsuarioSucesso", "resultado");
				} catch (ModeloException m) {
					MensagensDeErro
							.getErrorMessage(
									"editarCadastroUsuarioErroConfirmacao",
									"resultado");
					m.printStackTrace();
				}
			}
		} else {
			MensagensDeErro.getErrorMessage(
					"editarCadastroUsuarioErroAprovador", "resultado");
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

	public boolean isExibirAcessosTabela() {
		return (exibirAcessos && !marcadoAdministrador);
	}

	public boolean isExibirAcessos() {
		return exibirAcessos;
	}

	public void setExibirAcessos(boolean mostrar) {
		this.exibirAcessos = mostrar;
	}

	public void limpar() {

		this.acessos.clear();
		this.acessosAntigos.clear();
		this.administradores.clear();
		this.usuarios.clear();
		this.nome = null;
		this.nomeCompleto = null;
		this.telefone = null;
		this.mostrarLink = true;
		this.justificativa = null;
		this.exibirAcessos = false;

	}

	public boolean isMostrarLink() {
		return mostrarLink;
	}

	public void setMostrarLink(boolean mostrarLink) {
		this.mostrarLink = mostrarLink;
	}

	public List<Instituicao> getInstituicoes() {
		return instituicoes;
	}

	public void setInstituicoes(List<Instituicao> instituicoes) {
		this.instituicoes = instituicoes;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public boolean isRenderInstituicoes() {
		return renderInstituicoes;
	}

	public void setRenderInstituicoes(boolean renderInstituicoes) {
		this.renderInstituicoes = renderInstituicoes;
	}

	public boolean isMarcadoAdministrador() {
		return marcadoAdministrador;
	}

	public void setMarcadoAdministrador(boolean marcadoAdministrador) {
		this.marcadoAdministrador = marcadoAdministrador;
	}

	public boolean isExibirAdministrador() {

		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");

		if (usuario.isAdministrador() && exibirAcessos)
			return true;
		else
			return false;
	}

	public boolean isNotMarcadoAdministrador() {
		return !marcadoAdministrador;
	}

}