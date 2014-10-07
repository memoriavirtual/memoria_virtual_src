package br.usp.memoriavirtual.controle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.CadastrarInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.UtilMultimidiaRemote;
import br.usp.memoriavirtual.utils.MVControleMemoriaVirtual;
import br.usp.memoriavirtual.utils.MVModeloCamposMultimidia;
import br.usp.memoriavirtual.utils.MensagensDeErro;
import br.usp.memoriavirtual.utils.ValidacoesDeCampos;

@ManagedBean(name = "cadastrarInstituicaoMB")
@SessionScoped
public class CadastrarInstituicaoMB extends BeanContainerDeMidia implements
		BeanDeSessao, BeanMemoriaVirtual, Serializable {

	private static final long serialVersionUID = -6620103410985404517L;
	@EJB
	protected MemoriaVirtualRemote memoriaVirtualEJB;

	@EJB
	private CadastrarInstituicaoRemote cadastrarInstituicaoEJB;

	@EJB
	private UtilMultimidiaRemote utilMultimidiaEJB;

	protected String nome = "";
	protected String localidade = "";
	protected String endereco = "";
	protected String cidade = "";
	protected String estado = "";
	protected String pais = "";
	protected String cep = "";
	protected String telefone = "";
	protected String caixaPostal = "";
	protected String email = "";
	protected String url = "";
	protected String identificacaoProprietario = "";
	protected String administradorPropriedade = "";
	protected String latitude = "";
	protected String longitude = "";
	protected String altitude = "";
	protected Instituicao.TipoPropriedade tipoPropriedade = Instituicao.TipoPropriedade.privada;
	protected Instituicao.TiposProtecaoExistente protecaoExistente = Instituicao.TiposProtecaoExistente.decreto;
	protected String legislacao = "";
	protected String sinteseHistorica = "";
	protected String legislacaoIncidente = "";
	protected Boolean revisao = false;
	protected Instituicao instituicao = new Instituicao();
	private MensagensMB mensagens;
	

	public CadastrarInstituicaoMB() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(
				facesContext.getELContext(), null, "mensagensMB");
	}

	public String cadastrarInstituicao() {

		if (this.validar()) {

			Instituicao instituicao = new Instituicao();
			instituicao.setNome(this.nome);
			instituicao.setEndereco(this.endereco);
			instituicao
					.setAdministradorPropriedade(this.administradorPropriedade);
			instituicao.setAltitude(this.altitude);
			instituicao.setCaixaPostal(this.caixaPostal);
			instituicao.setCep(this.cep);
			instituicao.setCidade(this.cidade);
			instituicao.setEmail(this.email);
			instituicao.setEndereco(this.endereco);
			instituicao.setEstado(this.estado);
			instituicao
					.setIdentificacaoProprietario(this.identificacaoProprietario);
			instituicao.setLatitude(this.latitude);
			instituicao.setLegislacaoIncidente(this.legislacaoIncidente);
			instituicao.setLocalidade(this.localidade);
			instituicao.setLongitude(this.longitude);
			instituicao.setNome(this.nome);
			instituicao.setPais(this.pais);
			instituicao.setProtecaoExistente(this.protecaoExistente);
			instituicao.setSinteseHistorica(this.sinteseHistorica);
			instituicao.setTelefone(this.telefone);
			instituicao.setTipoPropriedade(this.tipoPropriedade);
			instituicao.setUrl(this.url);
			instituicao.setContainerMultimidia(this.containerMultimidia);
			instituicao.setValidade(true);
			instituicao.setRevisao(this.revisao);

			try {
				this.instituicao = cadastrarInstituicaoEJB
						.cadastrarInstituicao(instituicao);
				this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
				return this.redirecionar(
						"/restrito/cadastrarinstituicaomultimidia.jsf", true);
			} catch (ModeloException m) {
				m.printStackTrace();
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
				return this.redirecionar(null, false);
			}
		} else {
			this.getMensagens().mensagemErro(this.traduzir("erroFormulario"));
		}

		return this.redirecionar(null, false);
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

	public List<SelectItem> getTipoPropriedadeLista() {

		List<SelectItem> tiposPropriedade = new ArrayList<SelectItem>();

		for (Instituicao.TipoPropriedade t : Instituicao.TipoPropriedade
				.values()) {
			tiposPropriedade
					.add(new SelectItem(t, this.traduzir(t.toString())));
		}

		return tiposPropriedade;
	}

	public List<SelectItem> getProtecaoExistenteLista() {

		List<SelectItem> opcoes = new ArrayList<SelectItem>();

		for (Instituicao.TiposProtecaoExistente t : Instituicao.TiposProtecaoExistente
				.values()) {
			opcoes.add(new SelectItem(t, this.traduzir(t.toString())));
		}

		return opcoes;
	}

	public boolean validarNome() {

		if (this.nome == null || this.nome.equals("")) {
			String args[] = { this.traduzir("nome") };
			MensagensDeErro.getErrorMessage("erroCampoVazio", args,
					"validacao-nome");
			return false;
		} else if (!memoriaVirtualEJB
				.verificarDisponibilidadeNomeInstituicao(this.nome)) {
			MensagensDeErro.getErrorMessage("erroNomeJaCadastrado",
					"validacao-nome");
			return false;
		}
		return true;
	}
	
	public boolean validarCep(){
		if(ValidacoesDeCampos.validarFormatoCep(this.cep)){
			return true;
		}
		else{
			MensagensDeErro.getErrorMessage("erroFormatoCep","validacao-cep");
			return false;
		}
	}
	
	public boolean validarCaixaPostal(){
		if(ValidacoesDeCampos.validarFormatoCep(this.caixaPostal)){
			return true;
		}
		else{
			MensagensDeErro.getErrorMessage("erroFormatoCaixaPostal","validacao-caixa-postal");
			return false;
		}
	}
	public boolean validarTelefone(){
		if(ValidacoesDeCampos.validarFormatoTelefone(this.telefone)){
			return true;
		}
		else{
			MensagensDeErro.getErrorMessage("erroFormatoTelefone","validacao-telefone");
			return false;
		}
	}
	public boolean validarEmail(){
		if(ValidacoesDeCampos.validarFormatoEmail(this.email)){
			return true;
		}
		else{
			MensagensDeErro.getErrorMessage("erroFormatoEmail","validacao-email");
			return false;
		}
	}
	public boolean validarWebsite(){
		if(ValidacoesDeCampos.validarFormatoWebSite(this.url)){
			return true;
		}
		else{
			MensagensDeErro.getErrorMessage("erroFormatoUrl","validacao-url");
			return false;
		}
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

	public String cancelar() {

		this.limpar();
		return this.redirecionar("/restrito/index.jsf", true);

	}

	@Override
	public String traduzir(String chave) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, MVControleMemoriaVirtual.bundleName);
		return bundle.getString(chave);
	}

	@Override
	public String limpar() {
		this.nome = "";
		this.localidade = "";
		this.endereco = "";
		this.cidade = "";
		this.estado = "";
		this.pais = "";
		this.cep = "";
		this.telefone = "";
		this.caixaPostal = "";
		this.email = "";
		this.url = "";
		this.identificacaoProprietario = "";
		this.administradorPropriedade = "";
		this.latitude = "";
		this.longitude = "";
		this.altitude = "";
		this.tipoPropriedade = Instituicao.TipoPropriedade.privada;
		this.protecaoExistente = Instituicao.TiposProtecaoExistente.decreto;
		this.legislacao = "";
		this.sinteseHistorica = "";
		this.legislacaoIncidente = "";
		this.instituicao = new Instituicao();
		super.limpar();
		return null;
	}

	@Override
	public boolean validar() {
		return this.validarNome() && this.validarCep() 
				&& this.validarCaixaPostal() && this.validarTelefone()
				&& this.validarEmail() && this.validarWebsite();
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
				m.setContainerMultimidia(this.instituicao
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
	public String redirecionar(String pagina, boolean redirect) {
		return redirect ? pagina + "?faces-redirect=true" : pagina;
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

	// getters e setters comecam aqui
	public String getNome() {
		return this.nome;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public String getCidade() {
		return this.cidade;
	}

	public String getEstado() {
		return this.estado;
	}

	public String getCep() {
		return this.cep;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public String getPais() {
		return pais;
	}

	public String getCaixaPostal() {
		return caixaPostal;
	}

	public String getEmail() {
		return email;
	}

	public String getIdentificacaoProprietario() {
		return identificacaoProprietario;
	}

	public String getAdministradorPropriedade() {
		return administradorPropriedade;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setAdministradorPropriedade(String administradorPropriedade) {
		this.administradorPropriedade = administradorPropriedade;
	}

	public void setIdentificacaoProprietario(String identificacaoProprietario) {
		this.identificacaoProprietario = identificacaoProprietario;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCaixaPostal(String caixaPostal) {
		this.caixaPostal = caixaPostal;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Instituicao.TiposProtecaoExistente getProtecaoExistente() {
		return protecaoExistente;
	}

	public void setProtecaoExistente(
			Instituicao.TiposProtecaoExistente protecaoExistente) {
		this.protecaoExistente = protecaoExistente;
	}

	public void setNome(String pNome) {
		this.nome = pNome;
	}

	public void setEndereco(String pEndereco) {
		this.endereco = pEndereco;
	}

	public void setCidade(String pCidade) {
		this.cidade = pCidade;
	}

	public void setEstado(String pEstado) {
		this.estado = pEstado;
	}

	public void setCep(String pCep) {
		this.cep = pCep;
	}

	public void setTelefone(String pTelefone) {
		this.telefone = pTelefone;
	}

	public Instituicao.TipoPropriedade getTipoPropriedade() {
		return tipoPropriedade;
	}

	public void setTipoPropriedade(Instituicao.TipoPropriedade tipoPropriedade) {
		this.tipoPropriedade = tipoPropriedade;
	}

	public String getLegislacao() {
		return legislacao;
	}

	public void setLegislacao(String legislacao) {
		this.legislacao = legislacao;
	}

	public String getSinteseHistorica() {
		return sinteseHistorica;
	}

	public void setSinteseHistorica(String sinteseHistorica) {
		this.sinteseHistorica = sinteseHistorica;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLegislacaoIncidente() {
		return legislacaoIncidente;
	}

	public void setLegislacaoIncidente(String legislacaoIncidente) {
		this.legislacaoIncidente = legislacaoIncidente;
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public int getLimiteCampoTexto() {
		return ValidacoesDeCampos.LIMITE_PADRAO_CAMPO_TEXTO;
	}

	public Boolean getRevisao() {
		return revisao;
	}

	public void setRevisao(Boolean revisao) {
		this.revisao = revisao;
	}

	@Override
	public void validarCampo(String nomeCampoMensagem, String nomeCampo,String campo) {
		if(ValidacoesDeCampos.validarComprimento(campo, 255)){
			String args[] = {"255"};
			MensagensDeErro.getWarningMessage("erroMaximoCaracteres", args, nomeCampoMensagem);
		}		
	}
}
