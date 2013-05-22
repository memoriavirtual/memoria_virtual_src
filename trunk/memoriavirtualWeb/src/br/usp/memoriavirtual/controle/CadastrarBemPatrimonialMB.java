/**
 * 
 */
package br.usp.memoriavirtual.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;

import javax.faces.context.FacesContext;

import br.usp.memoriavirtual.modelo.entidades.ContainerMultimidia;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Assunto;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemArqueologico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemArquitetonico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemNatural;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Descritor;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Diagnostico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.DisponibilidadeUsoProtecao;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.HistoricoProcedencia;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Producao;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.Titulo;
import br.usp.memoriavirtual.modelo.fachadas.ModeloException;
import br.usp.memoriavirtual.utils.MensagensDeErro;

/**
 * @author bigmac
 * 
 */

public class CadastrarBemPatrimonialMB extends GerenciarBemPatrimonial{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4487901192049535944L;


	/**
	 * 
	 */
	public CadastrarBemPatrimonialMB() {
		super();
		this.geralTitulos.add(new Titulo());
//		this.apresentaAutorias.add(new ApresentaAutoria());
//		this.autorias.add(new Autoria());
	}

	
	/**
	 * 
	 */
	public String cadastrarBemPatrimonial() {

		FacesContext context = FacesContext.getCurrentInstance();
		String bundleName = "mensagens";
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		this.validacaoInstituicao();
		this.validacaoTitulo();
		if (!FacesContext.getCurrentInstance().getMessages().hasNext()) {
			
			if(this.geralTipoDoBemPatrimonial == null){
				this.geralTipoDoBemPatrimonial = "";
			}
			if (this.geralTipoDoBemPatrimonial.equalsIgnoreCase(bundle
					.getString("cadastrarBemTipoLista0"))) {
				// System.out.println("arqueologico");
				this.bemPatrimonial = new BemArqueologico(condicaoTopografica,
						sitioDaPaisagem, aguaProxima, possuiVegetacao,
						exposicao, usoAtual, descricaoOutros, descricaoNotas,
						this.areaTotal, this.comprimento, this.altura,
						this.largura, this.profundidade);
				this.bemPatrimonial.setDiagnostico(new Diagnostico(
						this.estadoConservPreserv, this.estadoConservNotas));

			} else if (this.geralTipoDoBemPatrimonial.equalsIgnoreCase(bundle
					.getString("cadastrarBemTipoLista4"))) {
				// System.out.println("edificado");
				this.bemPatrimonial = new BemArquitetonico(condicaoTopografica,
						this.uso, this.numPavimentos, numAmbientes, alcova,
						porao, sotao, descricaoOutros, areaTotal,
						alturaFachadaFrontal, this.alturaFachadaSuperior,
						this.largura, profundidade, alturaTotal,
						peDireitoTerreo, peDireitoTipo);
				this.bemPatrimonial.setDiagnostico(new Diagnostico(
						this.estadoPreser, this.estadoConser,
						this.estadoConservNotas));
			} else if (this.geralTipoDoBemPatrimonial.equalsIgnoreCase(bundle
					.getString("cadastrarBemTipoLista6"))) {
				// System.out.println("natural");
				this.bemPatrimonial = new BemNatural(relevo,
						caracteristicasAntropico, caracteristicasAmbientais);
				this.bemPatrimonial.setDiagnostico(new Diagnostico(
						this.estadoConservPreserv, this.estadoConservNotas));
			} else {
				this.bemPatrimonial.setTipoDoBemPatrimonial(new String(BemPatrimonial.TipoDoBemPatrimonial.NORMAL.name())); 
				this.bemPatrimonial.setDiagnostico(new Diagnostico(
						this.estadoConservPreserv, this.estadoConservNotas));
			}
			try {
				this.bemPatrimonial.setInstituicao(this.editarInstituicaoEJB
						.getInstituicao(geralNomeInstituicao));
			} catch (ModeloException e) {
				e.printStackTrace();
			}
			// anexando Geral Info
			this.bemPatrimonial.setTituloPrincipal(geralTituloPrincipal);
			this.bemPatrimonial.setTitulos(geralTitulos);
			this.bemPatrimonial.setColecao(geralColecao);
			this.bemPatrimonial.setExterno(geralExterno);
			this.bemPatrimonial.setLatitude(geralLatitude);
			this.bemPatrimonial.setNumeroDeRegistro(this.geralNumeroRegistro);
			this.bemPatrimonial.setLongitude(geralLongitude);
			this.bemPatrimonial.setComplemento(geralComplemento);
			// fim Geral info

			// anexando autorias
			for (int i = 0; i < this.autorias.size(); i++) {
				this.autorias.get(i).setBemPatrimonial(this.bemPatrimonial);
				this.autorias.get(i)
						.setTipoAutoria(
								this.getEnumTipoAutoria(this.apresentaAutorias
										.get(i).tipoAutoria));
			}
			this.bemPatrimonial.setAutorias(autorias);
			// fim autorias
			// anexando produ��o

			this.bemPatrimonial.setProducao(new Producao(this.producaoLocal,
					this.producaoAno, this.producaoEdicao,
					this.producaoOutrasRes));
			// fim anexando produ��o
			// System.out.println("normal");
			// anexando descri��o
			this.bemPatrimonial
					.setCaracteristicasFisTecExec(this.caracteristicasFisicas);

			// fim anexando descri��o

			// anexando Intervencao e diagnostico
			this.bemPatrimonial.setIntervencoes(intervencoes);
			this.bemPatrimonial.setDiagnostico(new Diagnostico(
					this.estadoPreser, this.estadoConser,
					this.estadoConservNotas));
			// fim anexando intervencao e diagnostico
			// anexando Disponibilidade Uso e Protec�o

			this.bemPatrimonial
					.setDisponibilidadeUsoProtecao(new DisponibilidadeUsoProtecao(
							this.disponibilidadeDoBem, this.condicoesDeAcesso,
							this.condicoesDeReproducao, this.dataDeRetorno,
							this.notasUsoAproveitamento, this.protecao,
							this.legislacaoNprocesso, this.instituicaoProtetora));
			// Fim anexando Disponibilidade Uso e Protec�o
			// anexado historio e procerdencia

			this.bemPatrimonial
					.setHistoricoProcedencia(new HistoricoProcedencia(
							this.tipoDeAquisicao,
							this.valorVenalEpocaTransacao,
							this.documentoDeAquisicao,
							this.primeiroPropietario, historico,
							this.intrumentoDePesquisa));

			// fim anexando historico e procedencia

			// anexando assuntos
			List<Assunto> assun = new ArrayList<Assunto>();
			
			String[] a = this.assunto.split(" ");
			for (int i = 0; i < a.length; i++) {
				assun.add(new Assunto());
				assun.get(i).setAssunto(a[i]);
			}
			this.bemPatrimonial.setAssuntos(new TreeSet<Assunto>(assun));
			// fim assuntos

			// anexando descritores
			List<Descritor> descr = new ArrayList<Descritor>();
			String[] b = this.descritores.split(" ");
			for (int i = 0; i < b.length; i++) {
				descr.add(new Descritor());
				descr.get(i).setDescritor(b[i]);
			}
			this.bemPatrimonial.setDescritores(new TreeSet<Descritor>(descr));
			// fim descritores

			// adcionando fontes de informa��o
			this.bemPatrimonial.setFontesInformacao(this.fontesInformacao);
			// fim adcionando fontes de informa��o
			this.bemPatrimonial.setPesquisadores(this.pesquisadores);
			ContainerMultimidia c = new ContainerMultimidia();
			for(Multimidia i : midias){
				c.addMultimidia(i);
			}
			this.bemPatrimonial.setContainerMultimidia(c);
			try {
				this.cadastrarBemPatrimonialEJB
						.cadastrarBemPatrimonial(this.bemPatrimonial);
			} catch (ModeloException e) {
				MensagensDeErro.getErrorMessage("cadastrarBemInstituicaoErro",
					 "resultado");
				e.printStackTrace();
				return null;
			}

			MensagensDeErro.getSucessMessage("cadastrarBemCadastrado",
					"resultado");
			
			this.zerarMB();
		} else {
			// MensagensDeErro.getErrorMessage("cadastrarBemInstituicaoErro",
			// "resultado");
		}
		return null;

	}

}
