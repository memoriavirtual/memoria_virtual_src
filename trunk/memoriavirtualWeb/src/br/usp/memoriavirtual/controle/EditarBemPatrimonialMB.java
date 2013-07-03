/**
 * 
 */
package br.usp.memoriavirtual.controle;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import br.usp.memoriavirtual.modelo.entidades.Autoria;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Assunto;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemArqueologico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemArquitetonico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemNatural;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Descritor;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Diagnostico;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarBemPatrimonialRemote;

/**
 * @author mac
 * 
 */
@SessionScoped
public class EditarBemPatrimonialMB extends GerenciarBemPatrimonial implements
		BeanComMidia, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2482974978856128676L;

	@EJB
	private EditarBemPatrimonialRemote editarBemPatrimonialEJB;

	//private String strDeBusca;
	//private List<BemPatrimonial> bemPatrimoniais = new ArrayList<BemPatrimonial>();
	private boolean etapa1 = true;
	private boolean etapa2 = false;
	//private boolean listarTodos = false;
	//@EJB
	//private RealizarBuscaSimplesRemote realizarBuscaEJB;
	

	/**
	 * 
	 */
	public EditarBemPatrimonialMB() {

	}

	public void listarBemPatrimonial(AjaxBehaviorEvent event) {

		this.listarBemPatrimonial();

	}

	public String selecionarBemPatrimonial() {
		return null;
	}

	

	
	
	public String selecionarBemPatrimonial(BemPatrimonial bemPatrimonial) {
		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);
		if (!bemPatrimonial.getTituloPrincipal().equals(
				bundle.getString("listarTodos"))) {

			intervencoes = bemPatrimonial.getIntervencoes();
			pesquisadores = bemPatrimonial.getPesquisadores();
			fontesInformacao = bemPatrimonial.getFontesInformacao();
			
			midias.clear();
			midias.addAll(bemPatrimonial
					.getContainerMultimidia().getMultimidia()) ;

			geralExterno = bemPatrimonial.isExterno();
			geralNomeInstituicao = bemPatrimonial.getInstituicao().getNome();
			// geralNaturezaBem = bemPatrimonial.get;
			geralTipoDoBemPatrimonial = bemPatrimonial
					.getTipoDoBemPatrimonial();
			geralNumeroRegistro = bemPatrimonial.getNumeroDeRegistro();
			geralColecao = bemPatrimonial.getColecao();
			geralComplemento = bemPatrimonial.getComplemento();
			geralLatitude = bemPatrimonial.getLatitude();
			geralLongitude = bemPatrimonial.getLongitude();
			geralTitulos = bemPatrimonial.getTitulos();
			
			geralTituloPrincipal = bemPatrimonial.getTituloPrincipal();
			autorias = bemPatrimonial.getAutorias();

			producaoLocal = bemPatrimonial.getProducao().getLocal();
			producaoAno = bemPatrimonial.getProducao().getAno();
			producaoEdicao = bemPatrimonial.getProducao().getEdicao();
			producaoOutrasRes = bemPatrimonial.getProducao()
					.getOutrasResponsabilidades();

			caracteristicasFisicas = bemPatrimonial
					.getCaracteristicasFisTecExec();

			// Arqueologico
			if (this.geralTipoDoBemPatrimonial.equalsIgnoreCase(bundle
					.getString("cadastrarBemTipoLista0"))) {
				condicaoTopografica = ((BemArqueologico) bemPatrimonial)
						.getCondicaoTopografica();
				sitioDaPaisagem = ((BemArqueologico) bemPatrimonial)
						.getSitioDaPaisagem();
				aguaProxima = ((BemArqueologico) bemPatrimonial)
						.getAguaProximo();
				possuiVegetacao = ((BemArqueologico) bemPatrimonial)
						.getPossuiVegetacao();
				exposicao = ((BemArqueologico) bemPatrimonial).getExposicao();
				usoAtual = ((BemArqueologico) bemPatrimonial).getUsoAtual();
				descricaoOutros = ((BemArqueologico) bemPatrimonial)
						.getOutros();
				descricaoNotas = ((BemArqueologico) bemPatrimonial).getNotas();
				this.areaTotal = ((BemArqueologico) bemPatrimonial)
						.getAreaTotal();
				this.comprimento = ((BemArqueologico) bemPatrimonial)
						.getComprimento();
				this.altura = ((BemArqueologico) bemPatrimonial).getAltura();
				this.largura = ((BemArqueologico) bemPatrimonial).getLargura();
				this.profundidade = ((BemArqueologico) bemPatrimonial)
						.getProfundidade();

				estadoConservPreserv = bemPatrimonial.getDiagnostico()
						.getEstPreservacao();
				this.estadoConservNotas = bemPatrimonial.getDiagnostico()
						.getNotaEstConservacao();
			} else if (this.geralTipoDoBemPatrimonial.equalsIgnoreCase(bundle
					.getString("cadastrarBemTipoLista4"))) {
				// Arquitetonico
				condicaoTopografica = ((BemArquitetonico) bemPatrimonial)
						.getCondicaoTopografia();
				this.uso = ((BemArquitetonico) bemPatrimonial).getUso();
				this.numPavimentos = ((BemArquitetonico) bemPatrimonial)
						.getNumeroDePavimentos();
				numAmbientes = ((BemArquitetonico) bemPatrimonial)
						.getNumeroDeAmbientes();
				alcova = ((BemArquitetonico) bemPatrimonial).getAlcova();
				porao = ((BemArquitetonico) bemPatrimonial).getPorao();
				sotao = ((BemArquitetonico) bemPatrimonial).getSotao();
				descricaoOutros = ((BemArquitetonico) bemPatrimonial)
						.getOutros();
				areaTotal = ((BemArquitetonico) bemPatrimonial).getAreaTotal();
				alturaFachadaFrontal = ((BemArquitetonico) bemPatrimonial)
						.getAlturaFachFrontal();
				this.alturaFachadaSuperior = ((BemArquitetonico) bemPatrimonial)
						.getAlturaFachPosterior();
				this.largura = ((BemArquitetonico) bemPatrimonial).getLargura();
				profundidade = ((BemArquitetonico) bemPatrimonial)
						.getProfundidade();
				alturaTotal = ((BemArquitetonico) bemPatrimonial)
						.getAlturaTotal();
				peDireitoTerreo = ((BemArquitetonico) bemPatrimonial)
						.getPeDireitoTerreo();
				peDireitoTipo = ((BemArquitetonico) bemPatrimonial)
						.getTipoPeDireito();

				this.estadoPreser = bemPatrimonial.getDiagnostico()
						.getEstPreservacao();
				this.estadoConser = bemPatrimonial.getDiagnostico()
						.getEstConservacao();
				this.estadoConservNotas = bemPatrimonial.getDiagnostico()
						.getNotaEstConservacao();
			} else if (this.geralTipoDoBemPatrimonial.equalsIgnoreCase(bundle
					.getString("cadastrarBemTipoLista6"))) {
				// System.out.println("natural");
				relevo = ((BemNatural) bemPatrimonial).getRelevo();
				caracteristicasAntropico = ((BemNatural) bemPatrimonial)
						.getMeioAntropico();
				caracteristicasAmbientais = ((BemNatural) bemPatrimonial)
						.getCaracteristicasAmbientais();

				this.estadoConservPreserv = ((BemNatural) bemPatrimonial)
						.getDiagnostico().getEstConservacao();
				this.estadoConservNotas = ((BemNatural) bemPatrimonial)
						.getDiagnostico().getNotaEstConservacao();
			} else {
				this.bemPatrimonial.setDiagnostico(new Diagnostico(
						this.estadoConservPreserv, this.estadoConservNotas));
			}

			disponibilidadeDoBem = bemPatrimonial
					.getDisponibilidadeUsoProtecao().getDisponibilidade();
			condicoesDeAcesso = bemPatrimonial.getDisponibilidadeUsoProtecao()
					.getCondicoesAcesso();
			dataDeRetorno = bemPatrimonial.getDisponibilidadeUsoProtecao()
					.getDataRetorno();
			condicoesDeReproducao = bemPatrimonial
					.getDisponibilidadeUsoProtecao().getCondicoesReproducao();
			notasUsoAproveitamento = bemPatrimonial
					.getDisponibilidadeUsoProtecao()
					.getNotasUsoAproveitamento();
			protecao = bemPatrimonial.getDisponibilidadeUsoProtecao()
					.getProtecao();
			instituicaoProtetora = bemPatrimonial
					.getDisponibilidadeUsoProtecao().getProtetoraInstituicao();
			legislacaoNprocesso = bemPatrimonial
					.getDisponibilidadeUsoProtecao().getLegislacao();

			tipoDeAquisicao = bemPatrimonial.getHistoricoProcedencia()
					.getTipoAquisicao();
			valorVenalEpocaTransacao = bemPatrimonial
					.getHistoricoProcedencia().getValorVenalTransacao();
			dataAquisicaoDocumento = bemPatrimonial.getHistoricoProcedencia()
					.getDataAquisicao();
			documentoDeAquisicao = bemPatrimonial.getHistoricoProcedencia()
					.getDadosDocTransacao();
			primeiroPropietario = bemPatrimonial.getHistoricoProcedencia()
					.getPrimeiroProprietario();
			historico = bemPatrimonial.getHistoricoProcedencia()
					.getHistorico();
			intrumentoDePesquisa = bemPatrimonial.getHistoricoProcedencia()
					.getInstrumentoPesquisa();

			for (Assunto a : bemPatrimonial.getAssuntos()) {
				assunto += (a.getAssunto() + " ");
			}
			for (Descritor a : bemPatrimonial.getDescritores()) {
				descritores += (a.getDescritor() + " ");
			}

			// apresentações
			int aux = 0;
			for (@SuppressWarnings("unused") Multimidia a : this.midias) {
				aux += 1;
				if ((aux % 4) == 1) {
					Integer mult = aux - 1;
					this.ApresentaMidias.add(mult);

				}
			}
			aux = 0;
			for (Autoria b : this.autorias) {
				if (aux < Autoria.TipoAutoria.values().length - 1) {
					aux += 1;
					ApresentaAutoria c = new ApresentaAutoria();
					c.setNomeAutor(b.getNomeAutor());
					c.setTipoAutoria(this.getTipoAutoria(b.getTipoAutoria()));
					this.apresentaAutorias.add(c);
					
				}
			}
			
			bensRelacionados = bemPatrimonial.getBensrelacionados();
						
			id = bemPatrimonial.getId();
			this.etapa1 = false;
			this.etapa2 = true;
		}
		
		strDeBusca = "";
		bemPatrimoniais.clear();
		return null;
	}
	public String salvarBemPatrimonial(){
		super.salvarBemPatrimonial();
		this.zerarMB();
		return null;
		
	}
	public String zerarMB(){
		super.zerarMB();
		this.etapa2 = false;
		this.etapa1 = true;
		strDeBusca = "";
		return null;
		
	}
	


	public boolean isEtapa1() {
		return etapa1;
	}

	public void setEtapa1(boolean etapa1) {
		this.etapa1 = etapa1;
	}

	public boolean isEtapa2() {
		return etapa2;
	}

	public void setEtapa2(boolean etapa2) {
		this.etapa2 = etapa2;
	}

	

}
