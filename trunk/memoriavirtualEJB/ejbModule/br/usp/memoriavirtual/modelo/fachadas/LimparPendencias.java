package br.usp.memoriavirtual.modelo.fachadas;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.usp.memoriavirtual.modelo.comandos.Comando;
import br.usp.memoriavirtual.modelo.comandos.ControleComandos;
import br.usp.memoriavirtual.modelo.entidades.Aprovacao;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.LimparPendenciasrRemote;


@Singleton
public class LimparPendencias implements LimparPendenciasrRemote {
	@Resource
	TimerService timerService;

	@EJB
	private MemoriaVirtualRemote memoriaVirtual;

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;

	private Logger logger = Logger
			.getLogger("br.usp.memoriavirtual.modelo.fachadas.Timer");

	
	public void criarTimer(long intervalDuration) {

		TimerConfig timerConfig = new TimerConfig();
		timerConfig.setInfo("Timer para tarefa de limpeza do banco.");
		timerConfig.setPersistent(true);

		timerService.createSingleActionTimer(intervalDuration, timerConfig);
	}

	@Timeout
	public void timeOutProgramado(javax.ejb.Timer timer) {

		long intervalo = 2;
		try {
			intervalo = Long.valueOf(this.memoriaVirtual.getIntervaloTimer());
		} catch (ModeloException e) {
			e.printStackTrace();
		}
		logger.info("Ocorreu o timer programado e foi programado outro para :" + intervalo + "segundos");
		/*Passa para hora o tempo de intervalo entre disparos*/
		this.criarTimer(intervalo ); //* 1000 * 60 * 60
		
		/*Realizar os procedimentos de limpeza*/
		ControleComandos controle = new ControleComandos();
		
		Query query = entityManager
				.createQuery("SELECT a FROM Aprovacao a");
		
		@SuppressWarnings("unchecked")
		List<Aprovacao> aprovacoes = (List<Aprovacao>) query.getResultList();
		for(Aprovacao aprov : aprovacoes){
			
			String name = aprov.getTabelaEstrangeira();
			
			StringTokenizer strToken = new StringTokenizer(name, ".");
			while(strToken.hasMoreTokens()){
				name = strToken.nextToken();
			}
			
			try {
				Class<?> cls = Class.forName(this.getClass().getPackage() + name);
				
				Class<?> parameter[] = new Class[1];  
	            parameter[0] = Aprovacao.class;  
	           
	            Constructor<?> ct = cls.getConstructor(parameter);  
	            
	            Object arglist[] = new Object[1];
	            arglist[0] = aprov;
	            
	            Object retobj = ct.newInstance(arglist);  
	            
	            controle.adicionar((Comando)retobj);
	            
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		controle.executeAll();
		
		//limpezaFabrica.limparConvites();
	}

	
	@Schedule(second = "*/1", minute = "*", hour = "*", persistent = true, info = "Gatilho")
	public void timeOutInicial() {

		/*Cria o primeiro timer para expirar em 2 segundos*/
		this.criarTimer(2000);

		/*	Pego a lista de Timers criados para está classe e cancelo o timer criado
		 * pelo container EJB via anotações.
		 */
		Collection<javax.ejb.Timer> timerList = timerService.getTimers();
		if(!timerList.isEmpty()){
			for(javax.ejb.Timer timer: timerList){
				if(timer.getInfo().equals("Gatilho")){
					timer.cancel();
				}
			}
		}else{
			logger.info("Nenhum timer criado.");
		}
	}
}
