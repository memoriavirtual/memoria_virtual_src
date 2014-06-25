package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarCadastroUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirUsuarioRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.utils.MVModeloAcao;
import br.usp.memoriavirtual.utils.MVModeloEmailParser;
import br.usp.memoriavirtual.utils.MVModeloEmailTemplates;
import br.usp.memoriavirtual.utils.MVModeloMapeamentoUrl;
import br.usp.memoriavirtual.utils.MVModeloParametrosEmail;

@ManagedBean(name = "excluirUsuarioMB")
@SessionScoped
public class ExcluirUsuarioMB extends EditarCadastroUsuarioMB implements
		Serializable {

	private static final long serialVersionUID = -6957046653709643226L;

	@EJB
	private ExcluirUsuarioRemote excluirUsuarioEJB;

	@EJB
	private MemoriaVirtualRemote memoriaVirtualEJB;

	@EJB
	private EditarCadastroUsuarioRemote editarCadastroUsuarioEJB;
	
	private MensagensMB mensagens;

	public ExcluirUsuarioMB() {
		super();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELResolver resolver = facesContext.getApplication().getELResolver();
		this.mensagens = (MensagensMB) resolver.getValue(facesContext.getELContext(), null, "mensagensMB");
	}

	@Override
	public String selecionarUsuario() {
		try {
			this.usuario = this.memoriaVirtualEJB.getUsuario(this.id);
			return this.redirecionar("/restrito/excluirusuario.jsf", true);
		} catch (ModeloException m) {
			this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
			m.printStackTrace();
			return null;
		}
	}

	public String solicitarExclusao() {

		if (this.validar()) {
			try {
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(new Date());
				calendario.add(Calendar.DATE, 30);
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String expiraEm = dateFormat.format(calendario.getTime());
				Aprovacao aprovacao = new Aprovacao();
				aprovacao.setAcao(MVModeloAcao.excluir_usuario);
				Usuario analista = memoriaVirtualEJB.getUsuario(this.analista);
				aprovacao.setAnalista(analista);
				aprovacao.setExpiraEm(calendario.getTime());
				aprovacao.setSolicitante(this.solicitante);
				String dados = "id;"
						+ new Long(this.usuario.getId()).toString()
						+ ";justificativa;" + this.justificativa;

				aprovacao.setDados(dados);

				long id = this.excluirUsuarioEJB.solicitarExclusao(
						this.usuario, aprovacao);

				Map<String, String> tags = new HashMap<String, String>();

				tags.put(MVModeloParametrosEmail.ANALISTA000.toString(),
						analista.getNomeCompleto());
				tags.put(MVModeloParametrosEmail.EXPIRACAO000.toString(),
						expiraEm);
				tags.put(MVModeloParametrosEmail.JUSTIFICATIVA000.toString(),
						this.justificativa);
				tags.put(MVModeloParametrosEmail.SOLICITANTE000.toString(),
						this.solicitante.getNomeCompleto());
				tags.put(MVModeloParametrosEmail.USUARIO000.toString(),
						this.usuario.getNomeCompleto());

				Map<String, String> parametros = new HashMap<String, String>();
				parametros.put("id", new Long(id).toString());
				String url = this.memoriaVirtualEJB.getUrl(
						MVModeloMapeamentoUrl.excluirUsuario, parametros);

				tags.put(MVModeloParametrosEmail.URL000.toString(), url);

				String email = new MVModeloEmailParser().getMensagem(tags,
						MVModeloEmailTemplates.excluirUsuario);
				String assunto = this.traduzir("excluirUsuarioAssuntoEmail");
				this.memoriaVirtualEJB.enviarEmail(analista.getEmail(),
						assunto, email);
				this.getMensagens().mensagemSucesso(this.traduzir("sucesso"));
				return this.redirecionar("/restrito/index.jsf", true);

			} catch (Exception e) {
				this.getMensagens().mensagemErro(this.traduzir("erroInterno"));
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}


	@Override
	public boolean validar() {
		return this.validarJustificativa();
	}

	public MensagensMB getMensagens() {
		return mensagens;
	}

	public void setMensagens(MensagensMB mensagens) {
		this.mensagens = mensagens;
	}

}
