package test;

public class TestesAutomatizados {
	
	public void testesLogin() throws InterruptedException {
		TesteLoginMemoriaVirtual login;
		
//		login = new TesteLoginMemoriaVirtual();
//		login.testeBase("mvirtual", "mvirtual");
//		login.encerrarPagina();
//		
//		login = new TesteLoginMemoriaVirtual();
//		login.testeFluxoAlternativo1();
//		login.encerrarPagina();
//		
		
		login = new TesteLoginMemoriaVirtual();
		login.testeFluxoAlternativo2();		
		login.encerrarPagina();
	}

	/**
	 * Método principal do teste automatizado de login
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main (String []args) throws InterruptedException{
		TestesAutomatizados testes = new TestesAutomatizados();
		
		testes.testesLogin();
	}

}
