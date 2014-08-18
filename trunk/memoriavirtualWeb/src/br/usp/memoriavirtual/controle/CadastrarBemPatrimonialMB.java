package br.usp.memoriavirtual.controle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Acesso;
import br.usp.memoriavirtual.modelo.entidades.Autor;
import br.usp.memoriavirtual.modelo.entidades.Autoria;
import br.usp.memoriavirtual.modelo.entidades.ContainerMultimidia;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Assunto;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Descritor;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Diagnostico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.DisponibilidadeUsoProtecao;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.HistoricoProcedencia;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Intervencao;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Pesquisador;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Titulo;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarBemPatrimonialRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarAutorRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.UtilMultimidiaRemote;
import br.usp.memoriavirtual.utils.MVControleMemoriaVirtual;
import br.usp.memoriavirtual.utils.MVModeloCamposMultimidia;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.StringContainer;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;

@ManagedBean(name = "cadastrarBemPatrimonialMB")
@SessionScoped
public class CadastrarBemPatrimonialMB extends BeanContainerDeMidia implements
		Serializable, BeanMemoriaVirtual {

	private static final long serialVersionUID = 4487901192049535944L;
	protected BemPatrimonial bemPatrimonial = new BemPatrimonial();
	protected String instituicao = "";
	protected List<StringContainer> fontesInformacao = new ArrayList<StringContainer>();
	protected ContainerMultimidia containerMultimidia = new ContainerMultimidia();
	protected ArrayList<Multimidia> midias = new ArrayList<Multimidia>();
	protected String assuntos = "";
	protected String descritores = "";
	protected String buscaInstituicao = "";
	protected String buscaBem = "";
	protected List<BemPatrimonial> bens = new ArrayList<BemPatrimonial>();

	@EJB
	private UtilMultimidiaRemote utilMultimidiaEJB;

	@EJB
	private CadastrarBemPatrimonialRemote cadastrarBemPatrimonialEJB;

	@EJB
	private EditarInstituicaoRemote editarInstituicaoEJB;

	@EJB
	protected EditarAutorRemote editarAutorEJB;

	private ValidacoesDeCampos validacao;
	private MensagensMB mensagens;

	public CadastrarBemPatrimonialMB() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(
				facesContext.getELContext(), null, "mensagensMB");
		this.validacao = (ValidacoesDeCampos) resolver.getValue(
				facesContext.getELContext(), null, "validacaoMB");
	}

	public String cadastrar() {

		if (this.validar()) {

			try {

				List<Titulo> titulosClone = new ArrayList<Titulo>();
				titulosClone.addAll(this.bemPatrimonial.getTitulos());

				for (Titulo t : titulosClone) {
					if (t.getValor().length() <= 0)
						this.bemPatrimonial.getTitulos().remove(t);
				}

				List<Intervencao> intervencoesClone = new ArrayList<Intervencao>();
				intervencoesClone.addAll(this.bemPatrimonial.getIntervencoes());

				for (Intervencao i : intervencoesClone) {
					if (i.getData().length() <= 0
							&& i.getDescricao().length() <= 0
							&& i.getResponsavel().length() <= 0)
						this.bemPatrimonial.getIntervencoes().remove(i);
				}

				List<Pesquisador> pesquisadoresClone = new ArrayList<Pesquisador>();
				pesquisadoresClone.addAll(this.bemPatrimonial
						.getPesquisadores());

				for (Pesquisador p : pesquisadoresClone) {
					if (p.getDataPesquisa().length() <= 0
							&& p.getNome().length() <= 0
							&& p.getNotasPesquisador().length() <= 0)
						this.bemPatrimonial.getPesquisadores().remove(p);
				}

				Instituicao i = this.editarInstituicaoEJB
						.getInstituicao(new Long(this.instituicao).longValue());
				this.bemPatrimonial.setInstituicao(i);

				Set<Assunto> assuntosSet = new TreeSet<Assunto>();
				String assuntosArray[] = assuntos.split("\\s+");

				for (String s : assuntosArray) {
					Assunto a = new Assunto();
					a.setAssunto(s);
					assuntosSet.add(a);
				}

				this.bemPatrimonial.setAssuntos(assuntosSet);

				Set<Descritor> descritoresSet = new TreeSet<Descritor>();
				String descritoresArray[] = this.descritores.split("\\s+");

				for (String s : descritoresArray) {
					Descritor d = new Descritor();
					d.setDescritor(s);
					descritoresSet.add(d);
				}

				this.bemPatrimonial.setDescritores(descritoresSet);

				List<String> fontesInformacaoLista = new ArrayList<String>();
				for (StringContainer s : this.fontesInformacao) {
					fontesInformacaoLista.add(s.getValor());
				}

				this.bemPatrimonial.setFontesInformacao(fontesInformacaoLista);

				this.bemPatrimonial = this.cadastrarBemPatrimonialEJB
						.cadastrarBemPatrimonial(bemPatrimonial);

				this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));

				return this
						.redirecionar(
								"/restrito/cadastrarbempatrimonialmultimidia.jsf",
								true);
			} catch (ModeloException e) {
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
				e.printStackTrace();
				return null;
			}
		} else {
			this.getMensagens().mensagemErro(this.traduzir("erroFormulario"));
			return null;
		}
	}

	public String cancelar() {
		this.limpar();
		return "/restrito/index.jsf";
	}

	public String limpar() {
		bemPatrimonial = new BemPatrimonial();
		fontesInformacao.clear();
		midias.clear();
		assuntos = "";
		descritores = "";
		buscaInstituicao = "";
		buscaBem = "";
		bens.clear();
		super.limpar();
		return null;
	}

	public String finalizar() {
		if (validarNomeMultimidia()) {
			for (MVModeloCamposMultimidia c : this.campos) {
				try {
					this.utilMultimidiaEJB.atualizarMidia(c.getId(),
							c.getNome(), c.getDescricao());
				} catch (ModeloException m) {
					m.printStackTrace();
					this.getMensagens().mensagemErro(
							this.traduzir("erroInterno"));
					return this.redirecionar(null, false);
				}
			}
			this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
			this.limpar();
			return redirecionar("/restrito/index.jsf", true);
		}
		return redirecionar(null, false);
	}

	public boolean validarNomeMultimidia() {
		boolean valido = true;
		for (MVModeloCamposMultimidia c : this.campos) {
			if (c.getNome().equals(null) || c.getNome() == null
					|| c.getNome().length() == 0) {
				this.getMensagens().mensagemErro(
						this.traduzir("erroFormulario"));
				valido = false;
			}
		}
		return valido;
	}

	public String adicionarTitulo() {
		this.bemPatrimonial.getTitulos().add(new Titulo());
		return null;
	}

	public String removerTitulo(Titulo t) {
		this.bemPatrimonial.getTitulos().remove(t);
		return null;
	}

	public String adicionarAutoria() {
		this.bemPatrimonial.getAutorias().add(new Autoria());
		return null;
	}

	public String removerAutoria(Autoria a) {
		this.bemPatrimonial.getAutorias().remove(a);
		return null;
	}

	public List<SelectItem> getTiposTitulo() {

		List<SelectItem> tipos = new ArrayList<SelectItem>();

		for (Titulo.TipoTitulo t : Titulo.TipoTitulo.values()) {
			tipos.add(new SelectItem(t, this.traduzir(t.toString())));
		}
		
		tipos.add(0, new SelectItem(null, ""));

		return tipos;
	}

	public List<SelectItem> getInstituicoes() {

		List<SelectItem> opcoes = new ArrayList<SelectItem>();
		List<Instituicao> instituicoes = null;
		Usuario usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuario");
		try {
			if (usuario.isAdministrador()) {
				instituicoes = editarInstituicaoEJB.listarInstituicoes(buscaInstituicao);
			} else {
				instituicoes = new ArrayList<Instituicao>();
				List<Instituicao> parcial = null;
				for (Acesso a : usuario.getAcessos()) {
					parcial = editarInstituicaoEJB.listarInstituicoes(buscaInstituicao,
							a.getGrupo(), usuario);
					instituicoes.addAll(parcial);
				}
			}
			for (Instituicao i : instituicoes) {
				opcoes.add(new SelectItem(new Long(i.getId()).toString(), i
						.getNome()));
			}
			return opcoes;
		} catch (Exception e) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			e.printStackTrace();
			return opcoes;
		}
	}

	public String adicionarBemRelacionado() {
		try {
			if (this.buscaBem.length() > 0) {
				BemPatrimonial bemPatrimonial = cadastrarBemPatrimonialEJB
						.getBemPatrimonial(new Long(this.buscaBem).longValue());

				if (!this.bemPatrimonial.getBensRelacionados().contains(
						bemPatrimonial)) {
					this.bemPatrimonial.getBensRelacionados().add(
							bemPatrimonial);
				}
				return null;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			return null;
		}
	}

	public String removerBemRelacionado(BemPatrimonial bemPatrimonial) {
		try {

			if (this.bemPatrimonial.getBensRelacionados().contains(
					bemPatrimonial)) {
				this.bemPatrimonial.getBensRelacionados()
						.remove(bemPatrimonial);
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			return null;
		}
	}

	public List<SelectItem> getAutores() {
		List<SelectItem> autores = new ArrayList<SelectItem>();
		try {
			List<Autor> autoresLista = this.editarAutorEJB.listarAutores("");
			for (Autor a : autoresLista) {
				autores.add(new SelectItem(a.getId(), a.getNome() + " "
						+ a.getSobrenome()));
			}
		} catch (Exception e) {
			return null;
		}
		autores.add(0, new SelectItem(null, ""));
		return autores;
	}

	public List<SelectItem> getTiposAutoria() {

		List<SelectItem> tipos = new ArrayList<SelectItem>();

		for (Autoria.TipoAutoria t : Autoria.TipoAutoria.values()) {
			tipos.add(new SelectItem(t, this.traduzir(t.toString())));
		}

		tipos.add(0, new SelectItem(null, ""));
		return tipos;
	}

	public String adicionarIntervencao() {
		this.bemPatrimonial.getIntervencoes().add(new Intervencao());
		return null;
	}

	public String removerIntervencao(Intervencao i) {
		this.bemPatrimonial.getIntervencoes().remove(i);
		return null;
	}

	public String adicionarFontesInformacao() {
		this.fontesInformacao.add(new StringContainer(""));
		return null;
	}

	public String removerFontesInformacao(StringContainer s) {
		this.fontesInformacao.remove(s);
		return null;
	}

	public String adicionarPesquisador() {
		this.bemPatrimonial.getPesquisadores().add(new Pesquisador());
		return null;
	}

	public String removerPesquisador(Pesquisador p) {
		this.bemPatrimonial.getPesquisadores().remove(p);
		return null;
	}

	@Override
	public String uploadFile() throws IOException {
		if (this.part.getSize() > 0) {

			String fileName = this.getFileName(this.part);

			if (fileName.equals(null) || fileName == null
					|| fileName.length() <= 0) {
				this.getMensagens().mensagemErro(
						this.traduzir("erroCampoNomeVazio"));
				return this.redirecionar(null, false);
			}

			InputStream inputStream = null;
			ByteArrayOutputStream out = null;
			try {
				inputStream = part.getInputStream();
				out = new ByteArrayOutputStream();

				int read = 0;
				final byte[] bytes = new byte[128];
				while ((read = inputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}

				out.toByteArray();
				Multimidia m = new Multimidia();
				m.setContentType(part.getContentType());
				m.setNome(fileName);
				m.setContent(out.toByteArray());
				m.setContainerMultimidia(this.bemPatrimonial
						.getContainerMultimidia());
				Long id = this.utilMultimidiaEJB.cadastrarMultimidia(m);
				MVModeloCamposMultimidia campo = new MVModeloCamposMultimidia();
				campo.setId(id);
				campo.setNome(m.getNome());
				campo.setDescricao(m.getDescricao());
				this.campos.add(campo);

			} catch (Exception e) {
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			}
		} else {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
		}
		return this.redirecionar(null, false);
	}

	public List<SelectItem> getCondicoesTopograficas() {

		List<SelectItem> opcoes = new ArrayList<SelectItem>();

		for (BemPatrimonial.condicoesTopograficas t : BemPatrimonial.condicoesTopograficas
				.values()) {
			opcoes.add(new SelectItem(t, this.traduzir(t.toString())));
		}
		
		opcoes.add(0, new SelectItem(null, ""));

		return opcoes;
	}

	public List<SelectItem> getTiposExposicao() {

		List<SelectItem> opcoes = new ArrayList<SelectItem>();

		for (BemPatrimonial.Exposicao t : BemPatrimonial.Exposicao.values()) {
			opcoes.add(new SelectItem(t, this.traduzir(t.toString())));
		}
		
		opcoes.add(0, new SelectItem(null, ""));

		return opcoes;
	}

	public List<SelectItem> getEstadosPreservacao() {

		List<SelectItem> opcoes = new ArrayList<SelectItem>();

		for (Diagnostico.EstadoPreservacao t : Diagnostico.EstadoPreservacao
				.values()) {
			opcoes.add(new SelectItem(t, this.traduzir(t.toString())));
		}
		
		opcoes.add(0, new SelectItem(null, ""));

		return opcoes;
	}

	public List<SelectItem> getEstadosConservacao() {

		List<SelectItem> opcoes = new ArrayList<SelectItem>();

		for (Diagnostico.EstadoConservacao t : Diagnostico.EstadoConservacao
				.values()) {
			opcoes.add(new SelectItem(t, this.traduzir(t.toString())));
		}
		
		opcoes.add(0, new SelectItem(null, ""));

		return opcoes;
	}

	public List<SelectItem> getTiposDisponibilidade() {

		List<SelectItem> opcoes = new ArrayList<SelectItem>();

		for (DisponibilidadeUsoProtecao.Disponibilidade t : DisponibilidadeUsoProtecao.Disponibilidade
				.values()) {
			opcoes.add(new SelectItem(t, this.traduzir(t.toString())));
		}
		
		opcoes.add(0, new SelectItem(null, ""));

		return opcoes;
	}

	public List<SelectItem> getCondicoesAcesso() {

		List<SelectItem> opcoes = new ArrayList<SelectItem>();

		for (DisponibilidadeUsoProtecao.CondicoesAcesso t : DisponibilidadeUsoProtecao.CondicoesAcesso
				.values()) {
			opcoes.add(new SelectItem(t, this.traduzir(t.toString())));
		}
		
		opcoes.add(0, new SelectItem(null, ""));

		return opcoes;
	}

	public List<SelectItem> getCondicoesReproducao() {

		List<SelectItem> opcoes = new ArrayList<SelectItem>();

		for (DisponibilidadeUsoProtecao.CondicoesReproducao t : DisponibilidadeUsoProtecao.CondicoesReproducao
				.values()) {
			opcoes.add(new SelectItem(t, this.traduzir(t.toString())));
		}
		
		opcoes.add(0, new SelectItem(null, ""));

		return opcoes;
	}

	public List<SelectItem> getProtecoes() {

		List<SelectItem> opcoes = new ArrayList<SelectItem>();

		for (DisponibilidadeUsoProtecao.Protecao t : DisponibilidadeUsoProtecao.Protecao
				.values()) {
			opcoes.add(new SelectItem(t, this.traduzir(t.toString())));
		}
		
		opcoes.add(0, new SelectItem(null, ""));

		return opcoes;
	}

	public List<SelectItem> getTiposAquisicao() {

		List<SelectItem> opcoes = new ArrayList<SelectItem>();

		for (HistoricoProcedencia.TipoAquisicao t : HistoricoProcedencia.TipoAquisicao
				.values()) {
			opcoes.add(new SelectItem(t, this.traduzir(t.toString())));
		}
		
		opcoes.add(0, new SelectItem(null, ""));

		return opcoes;
	}

	// getters e setters
	public BemPatrimonial getBemPatrimonial() {
		return bemPatrimonial;
	}

	public void setBemPatrimonial(BemPatrimonial bemPatrimonial) {
		this.bemPatrimonial = bemPatrimonial;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public String getDescritores() {
		return descritores;
	}

	public void setDescritores(String descritores) {
		this.descritores = descritores;
	}

	public String getAssuntos() {
		return assuntos;
	}

	public void setAssuntos(String assuntos) {
		this.assuntos = assuntos;
	}

	public List<StringContainer> getFontesInformacao() {
		return fontesInformacao;
	}

	public void setFontesInformacao(List<StringContainer> fontesInformacao) {
		this.fontesInformacao = fontesInformacao;
	}

	public List<BemPatrimonial> getBens() {
		return bens;
	}

	public void setBens(List<BemPatrimonial> bens) {
		this.bens = bens;
	}

	@Override
	public String removerMidia(Long id) {
		try {
			this.utilMultimidiaEJB.excluirMultimidia(id.longValue());
			MVModeloCamposMultimidia remover = null;
			for (MVModeloCamposMultimidia c : this.campos) {
				if (c.getId().equals(id)) {
					remover = c;
				}
			}
			this.campos.remove(remover);
			return this.redirecionar(null, false);
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return this.redirecionar(null, false);
		}
	}

	@Override
	public String imagemDisplay(Long id) {
		try {
			if (id.equals(null) || id == null) {
				throw new ModeloException(
						"Campo ID para multimidia não pode ser null");
			}
			String tipo = this.utilMultimidiaEJB.getContentType(id.longValue());
			return tipo.contains("image") ? "" : "none";
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return null;
		}
	}

	@Override
	public String videoDisplay(Long id) {
		try {
			if (id.equals(null) || id == null) {
				throw new ModeloException(
						"Campo ID para multimidia não pode ser null");
			}
			String tipo = this.utilMultimidiaEJB.getContentType(id.longValue());
			return tipo.contains("video") ? "" : "none";
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return null;
		}
	}

	@Override
	public String midiaDisplay(Long id) {
		try {
			if (id.equals(null) || id == null) {
				throw new ModeloException(
						"Campo ID para multimidia não pode ser null");
			}
			String tipo = this.utilMultimidiaEJB.getContentType(id.longValue());
			return !tipo.contains("image") && !tipo.contains("video") ? ""
					: "none";
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return null;
		}
	}

	@Override
	public String getContentType(Long id) {
		try {
			return this.utilMultimidiaEJB.getContentType(id.longValue());
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return null;
		}
	}

	@Override
	public String traduzir(String chave) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, MVControleMemoriaVirtual.bundleName);
		return bundle.getString(chave);
	}

	@Override
	public String redirecionar(String pagina, boolean redirect) {
		return redirect ? pagina + "?faces-redirect=true" : pagina;
	}

	@Override
	public boolean validar() {
		return this.validarTituloPrincipal() && this.validarNumeroRegistro();
	}
	
	public void validarLatitude(String nomeCampo){
		
	}
	
	public void validarLongitude(String nomeCampo){
		
	}
	
	public void validarCampo(String nomeCampoMensagem, String nomeCampo, String campo){
		if(ValidacoesDeCampos.validarComprimento(campo, 255)){
			String args[] = {"255"};
			MensagensDeErro.getErrorMessage("erroMaximoCaracteres", args, nomeCampoMensagem);
		}
	}
	
	public void validarCampoComHeranca(String nomeInicialComponente,String nomeCampoMensagem, String nomeCampo, String campo){
		String componente = nomeInicialComponente.split(":")[0] +":"+ nomeInicialComponente.split(":")[1] + ":";
		componente = componente + nomeCampoMensagem;
		if(ValidacoesDeCampos.validarComprimento(campo, 255)){
			String args[] = {"255"};
			MensagensDeErro.getErrorMessage("erroMaximoCaracteres", args, componente);
		}
	}
	
	public String gerarComponenteValidacaoComHeranca(String componente, String campoValidacao){
		String comp = componente.split(":")[0] +":"+ componente.split(":")[1] + ":";
		comp = comp + campoValidacao;
		return comp;
	}
	
	public boolean validarTituloPrincipal() {
		if (this.bemPatrimonial.getTituloPrincipal() == null
				|| this.bemPatrimonial.getTituloPrincipal().equals("")) {
			String args[] = { this.traduzir("tituloPrincipal") };
			MensagensDeErro.getErrorMessage("erroCampoVazio", args,
					"validacao-titulo-principal");
			return false;
		}
		return true;
	}

	public boolean validarNumeroRegistro() {
		if (ValidacoesDeCampos.validarNaoNulo((Object) this.bemPatrimonial.getNumeroRegistro())
				&& ValidacoesDeCampos.validarStringNaoVazia(this.bemPatrimonial.getNumeroRegistro())) {
			try {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("numero",
						(Object) this.bemPatrimonial.getNumeroRegistro());
				if (this.validacao.validarNaoExiste("unicoRegistro",
						(Object) this.bemPatrimonial, parametros)) {
					return true;
				} else {
					String args[] = { this.traduzir("numeroRegistro"),
							this.bemPatrimonial.getNumeroRegistro() };
					MensagensDeErro.getErrorMessage("erroJaExiste", args,
							"validacao-numero-registro");
					return false;
				}
			} catch (Exception e) {
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
				e.printStackTrace();
				return false;
			}
		} else {
			String args[] = { this.traduzir("numeroRegistro") };
			MensagensDeErro.getErrorMessage("erroCampoVazio", args,"validacao-numero-registro");
			return false;
		}
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}

	public String getBuscaInstituicao() {
		return buscaInstituicao;
	}

	public void setBuscaInstituicao(String buscaInstituicao) {
		this.buscaInstituicao = buscaInstituicao;
	}

	public String getBuscaBem() {
		return buscaBem;
	}

	public void setBuscaBem(String buscaBem) {
		this.buscaBem = buscaBem;
	}
}
