package br.usp.memoriavirtual.modelo.comandos;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;

@Singleton
public class ControleComandos {

	private List<Comando> comandos;
	
	public ControleComandos(){
		comandos = new ArrayList<Comando>();
		
	}
	
	public void adicionar(Comando comando){
		this.comandos.add(comando);
	}
	
	public void execute(Comando comando){
		comando.executar();
	}
	
	public void executeAll(){
		for(Comando cmd:comandos){
			cmd.executar();
		}
	}
	
	public void cancelarComando(Comando comando){
		comandos.remove(comando);
	}
}
