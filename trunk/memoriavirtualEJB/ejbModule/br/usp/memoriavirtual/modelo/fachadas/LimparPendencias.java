package br.usp.memoriavirtual.modelo.fachadas;

import java.util.Collection;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

import br.usp.memoriavirtual.modelo.fachadas.remoto.LimparPendenciasrRemote;
import br.usp.memoriavirtual.modelo.fachadas.remoto.MemoriaVirtualRemote;


@Singleton
public class LimparPendencias implements LimparPendenciasrRemote {
	@Resource
	TimerService timerService;

	@EJB
	private MemoriaVirtualRemote memoriaVirtual;


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
		this.criarTimer(intervalo * 1000 * 60 * 60);
		
		/*Realiza chamada aos métodos de limpeza*/
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
