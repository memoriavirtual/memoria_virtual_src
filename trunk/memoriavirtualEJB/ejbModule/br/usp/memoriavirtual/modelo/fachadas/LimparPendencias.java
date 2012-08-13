package br.usp.memoriavirtual.modelo.fachadas;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Date;
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
import br.usp.memoriavirtual.modelo.fachadas.remoto.LimparPendenciasrRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;


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
			
			/*Atualiza o valor que está no banco de dado*/
			entityManager.refresh(aprov);
			
			/*Cria a data atual e verifica se já expirou a data da aprovação*/
			Date dataAtual = new Date();
			if(dataAtual.before(aprov.getExpiracao()))
				continue;
			
			/*Extraimos o nome da Entidade que gerou a dependencia*/
			String name = aprov.getTabelaEstrangeira();
			
			StringTokenizer strToken = new StringTokenizer(name, ".");
			while(strToken.hasMoreTokens()){
				name = strToken.nextToken();
			}
			
			/*Tenta-se criar um comando baseado na entidade que gerou a dependencia*/
			try {
				/*Esse eh o package onde se encontra as implementacoes da interface comandos*/
				String nomeDaDependencia = "br.usp.memoriavirtual.modelo.comandos.pendencias." + name;
				
				/*Aqui utiliza-se Java Reflection para criar a instancia de uma classe escolhida dinamicamente*/
				Class<?> cls = Class.forName(nomeDaDependencia);

				
				Class<?> parameter[] = new Class[1];  
	            parameter[0] = Aprovacao.class;  
	           
	            Constructor<?> ct = cls.getConstructor(parameter);  
	            
	            Object arglist[] = new Object[1];
	            arglist[0] = aprov;
	            
	            Object retobj = ct.newInstance(arglist);  
	            
	            Comando cmd = (Comando)retobj;
				
	            /*Adiciona-se na lista de comandos a ser executados para exclusão da dependencia*/
	            controle.adicionar(cmd);
	            
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		controle.executeAll();

	}

	
	@Schedule(second = "*/1", minute = "*", hour = "*", persistent = true, info = "Gatilho")
	public void timeOutInicial() {

		/*Cria o primeiro timer para expirar em 2 segundos*/
		this.criarTimer(2000);

		/*	Pego a lista de Timers criados para est� classe e cancelo o timer criado
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
