/**
 * 
 */
package br.usp.memoriavirtual.modelo.fachadas;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemArqueologico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemArquitetonico;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemNatural;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.remoto.EditarBemPatrimonialRemote;

/**
 * @author mac
 *
 */
@Stateless (mappedName = "EditarBemPatrimonial")
public class EditarBemPatrimonial implements EditarBemPatrimonialRemote {
	public EditarBemPatrimonial() {
		
	}
	
	
	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<BemPatrimonial> listarBensPatrimoniais (String strDeBusca) throws ModeloException{
		List<BemPatrimonial> lista ;

		Query query;
		query = this.entityManager
				.createQuery("SELECT a FROM BemPatrimonial a ");
		try {
			lista = ( List<BemPatrimonial> ) query.getResultList();
			return  lista ;
		} catch (Exception e) {
			throw new ModeloException(e);
		}
	}

	@Override
	public void salvarBemPatrimonial(BemPatrimonial bem) throws ModeloException {
		
		
		BemPatrimonial bemOld;
		
		bemOld = this.entityManager.find(BemPatrimonial.class,  bem.getId());
		
		if(bemOld != null){
			try {
				bemOld.setInstituicao(bem.getInstituicao());
			
			if (bem.getTipoDoBemPatrimonial().equals(new String(BemPatrimonial.TipoDoBemPatrimonial.ARQUEOLOGICO.name()))) {
				// System.out.println("arqueologico");
				((BemArqueologico)bemOld).setCondicaoTopografica(((BemArqueologico)bem).getCondicaoTopografica());
				((BemArqueologico)bemOld).setSitioDaPaisagem(((BemArqueologico)bem).getSitioDaPaisagem());
				((BemArqueologico)bemOld).setAguaProximo(((BemArqueologico)bem).getAguaProximo());
				((BemArqueologico)bemOld).setPossuiVegetacao(((BemArqueologico)bem).getPossuiVegetacao());
				((BemArqueologico)bemOld).setExposicao(((BemArqueologico)bem).getExposicao());
				((BemArqueologico)bemOld).setUsoAtual(((BemArqueologico)bem).getUsoAtual());
				((BemArqueologico)bemOld).setOutros(((BemArqueologico)bem).getOutros());
				((BemArqueologico)bemOld).setNotas(((BemArqueologico)bem).getNotas());
				((BemArqueologico)bemOld).setAreaTotal(((BemArqueologico)bem).getAreaTotal());
				((BemArqueologico)bemOld).setComprimento(((BemArqueologico)bem).getComprimento());
				((BemArqueologico)bemOld).setAltura(((BemArqueologico)bem).getAltura());
				((BemArqueologico)bemOld).setLargura(((BemArqueologico)bem).getLargura());
				((BemArqueologico)bemOld).setProfundidade(((BemArqueologico)bem).getProfundidade());
						  
						
				((BemArqueologico)bemOld).getDiagnostico().setEstConservacao(((BemArqueologico)bem).getDiagnostico().getEstConservacao());
				((BemArqueologico)bemOld).getDiagnostico().setNotaEstConservacao(((BemArqueologico)bem).getDiagnostico().getNotaEstConservacao());
				
				
			} else if (bem.getTipoDoBemPatrimonial().equals(new String(BemPatrimonial.TipoDoBemPatrimonial.ARQUITETONICO.name()))) {
				// System.out.println("edificado");
				((BemArquitetonico)bemOld).setProfundidade(((BemArquitetonico)bem).getProfundidade());
				((BemArquitetonico)bemOld).setCondicaoTopografia(((BemArquitetonico)bem).getCondicaoTopografia());
				((BemArquitetonico)bemOld).setUso(((BemArquitetonico)bem).getUso());
				((BemArquitetonico)bemOld).setNumeroDePavimentos(((BemArquitetonico)bem).getNumeroDePavimentos());
				((BemArquitetonico)bemOld).setNumeroDeAmbientes(((BemArquitetonico)bem).getNumeroDeAmbientes());
				((BemArquitetonico)bemOld).setAlcova(((BemArquitetonico)bem).getAlcova());
				((BemArquitetonico)bemOld).setPorao(((BemArquitetonico)bem).getPorao());
				((BemArquitetonico)bemOld).setSotao(((BemArquitetonico)bem).getSotao());
				((BemArquitetonico)bemOld).setOutros(((BemArquitetonico)bem).getOutros());
				((BemArquitetonico)bemOld).setAreaTotal(((BemArquitetonico)bem).getAreaTotal());
				((BemArquitetonico)bemOld).setAlturaFachFrontal(((BemArquitetonico)bem).getAlturaFachFrontal());
				((BemArquitetonico)bemOld).setAlturaFachPosterior(((BemArquitetonico)bem).getAlturaFachPosterior());
				((BemArquitetonico)bemOld).setLargura(((BemArquitetonico)bem).getLargura());
				((BemArquitetonico)bemOld).setAlturaTotal(((BemArquitetonico)bem).getAlturaTotal());
				((BemArquitetonico)bemOld).setPeDireitoTerreo(((BemArquitetonico)bem).getPeDireitoTerreo());	
				((BemArquitetonico)bemOld).setTipoPeDireito(((BemArquitetonico)bem).getTipoPeDireito());
				
				
				((BemArquitetonico)bemOld).getDiagnostico().setEstConservacao(((BemArquitetonico)bem).getDiagnostico().getEstConservacao());
				((BemArquitetonico)bemOld).getDiagnostico().setEstPresercacao(((BemArquitetonico)bem).getDiagnostico().getEstPresercacao());
				((BemArquitetonico)bemOld).getDiagnostico().setNotaEstConservacao(((BemArquitetonico)bem).getDiagnostico().getNotaEstConservacao());
						
			} else if (bem.getTipoDoBemPatrimonial().equals(new String(BemPatrimonial.TipoDoBemPatrimonial.NATURAL.name()))) {
				// System.out.println("natural");
				((BemNatural)bemOld).setRelevo(((BemNatural)bem).getRelevo());
				((BemNatural)bemOld).setMeioAntropico(((BemNatural)bem).getMeioAntropico());
				((BemNatural)bemOld).setCaracteristicasAmbientais(((BemNatural)bem).getCaracteristicasAmbientais());
				((BemNatural)bemOld).setCaracteristicasAmbientais(((BemNatural)bem).getCaracteristicasAmbientais());
				
						
				((BemNatural)bemOld).getDiagnostico().setEstPresercacao(((BemNatural)bem).getDiagnostico().getEstPresercacao());
				((BemNatural)bemOld).getDiagnostico().setNotaEstConservacao(((BemNatural)bem).getDiagnostico().getNotaEstConservacao());
			} else {
				
				bemOld.getDiagnostico().setEstPresercacao(bem.getDiagnostico().getEstPresercacao());
				bemOld.getDiagnostico().setNotaEstConservacao(bem.getDiagnostico().getNotaEstConservacao());
			}

			//anexando Geral Info

			
			bemOld.getTitulos().clear();
			bemOld.getTitulos().addAll(bem.getTitulos());
			bemOld.setColecao(bem.getColecao());
			bemOld.setExterno(bem.isExterno());
			bemOld.setLatitude(bem.getLatitude());
			bemOld.setNumeroDeRegistro(bem.getNumeroDeRegistro());
			bemOld.setLongitude(bem.getLongitude());
			bemOld.setComplemento(bem.getComplemento());
			// fim Geral info

			// anexando autorias
			bemOld.getAutorias().clear();
			bemOld.getAutorias().addAll(bem.getAutorias());
			// fim autorias
			// anexando produção

			bemOld.setProducao(bem.getProducao());
			
			// fim anexando produção
			// System.out.println("normal");
			// anexando descrição
			bemOld
					.setCaracteristicasFisTecExec(bem.getCaracteristicasFisTecExec());

			// fim anexando descrição

			// anexando Intervencao e diagnostico
			bemOld.getIntervencoes().clear();
			bemOld.setIntervencoes(bem.getIntervencoes());
			bemOld.setDiagnostico(bem.getDiagnostico());
			// fim anexando intervencao e diagnostico
			// anexando Disponibilidade Uso e Protecão

			bemOld
					.setDisponibilidadeUsoProtecao(bem.getDisponibilidadeUsoProtecao());
			// Fim anexando Disponibilidade Uso e Protecão
			// anexado historio e procerdencia

			bemOld
					.setHisttoricoProcedencia(bem.getHisttoricoProcedencia());

			// fim anexando historico e procedencia

			// anexando assuntos
			bemOld.getAssuntos().clear();
			bemOld.setAssuntos(bem.getAssuntos());
			// fim assuntos

			// anexando descritores
			bemOld.getDescritores().clear();
			bemOld.setDescritores(bem.getDescritores());
			// fim descritores

			// adcionando fontes de informação
			bemOld.setFontesInformacao(bem.getFontesInformacao());
			// fim adcionando fontes de informação
			bemOld.getPesquisadores().clear();
			bemOld.setPesquisadores(bem.getPesquisadores());
			
			bemOld.getContainerMultimidia().getMultimidia().clear();
			bemOld.getContainerMultimidia().setMultimidia(bem.getContainerMultimidia().getMultimidia());
			
			
			
			
			
				
			} catch (Exception e) {
				throw new ModeloException(e);
				
			}
		}
		
		try {
			entityManager.flush();
		} catch (Exception t) {
			throw new ModeloException(t);
		}
	}
	

}
