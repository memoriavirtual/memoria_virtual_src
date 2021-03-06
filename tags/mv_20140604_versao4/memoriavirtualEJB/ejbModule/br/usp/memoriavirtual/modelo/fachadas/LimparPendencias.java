package br.usp.memoriavirtual.modelo.fachadas;


import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.entidades.Instituicao;
import br.usp.memoriavirtual.modelo.entidades.Usuario;
import br.usp.memoriavirtual.modelo.fachadas.remoto.ExcluirInstituicaoRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.LimparPendenciasrRemote;
import br.usp.memoriavirtual.utils.MVModeloAcao;

@Stateless
@Startup
public class LimparPendencias implements LimparPendenciasrRemote {
	 
	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager em;
	
	@EJB
	private ExcluirInstituicaoRemote excluirInstituicao;
	
	public LimparPendencias(){
		
	}
	
	@SuppressWarnings("unchecked")
	@Schedule
	public void executa() throws ModeloException{
		
		java.util.Calendar cal = java.util.Calendar.getInstance(); 
		Date dataAtual = new Date(cal.getTimeInMillis());
		
		//Date dataAtual = new Date(2014,12,12); //usado para testes
		
		System.out.println("Executando Limpar P�ndencias: "+dataAtual);
		
		List<Usuario> usuariosExpirados = null;
		List<Aprovacao> aprovacoesExpiradas = null;
		
		
		Query q = em.createQuery("Select u from Usuario u WHERE u.validade <:dataAtual");
		q.setParameter("dataAtual", dataAtual);	
		usuariosExpirados =  q.getResultList();
		
		q = em.createQuery("Select a from Aprovacao a WHERE a.expiraEm <:dataAtual");
		q.setParameter("dataAtual", dataAtual);
		aprovacoesExpiradas = q.getResultList();
		
		
		for(int i=0;i<usuariosExpirados.size();i++)
			System.out.println("Usuario Expirado(email):"+usuariosExpirados.get(i).getEmail());
		
		for(int i=0;i<aprovacoesExpiradas.size();i++)
			System.out.println("Aprova��o Expirada(aprovador):"+aprovacoesExpiradas.get(i).getAnalista());
		
		for(int i=0;i<usuariosExpirados.size();i++){
			Usuario u = usuariosExpirados.get(i);
			em.remove(u);			
		}
		
		for (Aprovacao aprov : aprovacoesExpiradas) {

			/* Extraimos o nome da A��o que gerou a p�ndencia */
			MVModeloAcao acao = aprov.getAcao();

			if(acao.equals(MVModeloAcao.excluir_usuario)){
				String[] dados = aprov.getDados().split(";");
				Usuario user = em.find(Usuario.class, dados[1]);
				user.setAtivo(true); 
				em.merge(user);

			}else if(acao.equals(MVModeloAcao.excluir_instituicao)){
				String[] dados = aprov.getDados().split(";");
				Instituicao inst = em.find(Instituicao.class,Long.parseLong(dados[1]));
				inst.setValidade(true); 
				em.merge(inst);
			}
			em.remove(aprov);			
		}
	}
}
