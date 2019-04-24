package test;

public class TestesAutomatizados {

	/**
	 * 
	 * @throws InterruptedException
	 */
	public void testesRealizarLogin() throws InterruptedException {
		TesteLogin login;
		
		login = new TesteLogin();
		login.loginFluxoPrincipal("mvirtual", "mvirtual");
		login.encerrarPagina();
		
		login = new TesteLogin();
		login.loginFluxoAlternativo1();
		login.encerrarPagina();
		
		
		login = new TesteLogin();
		login.loginFluxoAlternativo2();		
		login.encerrarPagina();
	}
	
	/**
	 * 
	 * @throws InterruptedException
	 */
	public void testesRealizarLogoff() throws InterruptedException{
		TesteLogoff logoff = new TesteLogoff();
		logoff.logoffFluxoPrincipal();
		logoff.encerrarPagina();
	}
	
	
	/**
	 * Método principal dos testes automatizados
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		TestesAutomatizados testes = new TestesAutomatizados();
		
		
		testes.testesRealizarLogin();
		testes.testesRealizarLogoff();
	}

}
