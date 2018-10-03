package test;

public class Main {

	/**
	 * Método principal do teste automatizado de login
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main (String []args) throws InterruptedException{
		TesteLoginMemoriaVirtual login = new TesteLoginMemoriaVirtual();
		
		login.abrirPaginaLogin();
		login.setElementosDaPagina();
		
		//login.testeBase("mvirtual", "mvirtual");
		//login.testeFluxoAlternativo1();
		login.testeFluxoAlternativo2();
		
		login.encerrarPagina();
	}

}
