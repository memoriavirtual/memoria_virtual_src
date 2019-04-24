package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Teste do caso de uso "Realizar Logoff".
 * Herda a classe TesteLogin.
 * @author Fernando
 *
 */
public class TesteLogoff extends TesteLogin{
	
	private WebElement logoffLink;
	
	/**
	 * Método construtor que chama o construtor da classe-mãe.
	 */
	public TesteLogoff() {
		super();
	}
	
	/**
	 * Fluxo principal do caso de uso "Realizar Login".
	 * @throws InterruptedException
	 */
	public void logoffFluxoPrincipal() throws InterruptedException{		
		this.loginFluxoPrincipal("mvirtual", "mvirtual");
		
		logoffLink = this.driver.findElement(By.linkText("Sair"));
		
		logoffLink.click();
		
		Thread.sleep(1000); //dando 1 segundo(s) para o usuário ver o teste
	}
}
