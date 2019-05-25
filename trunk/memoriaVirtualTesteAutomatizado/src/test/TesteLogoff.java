package test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
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
	@Test
	public void logoffFluxoPrincipal() throws InterruptedException{		
		this.loginFluxoPrincipal();
		
		logoffLink = this.driver.findElement(By.id("formCabecalho:sair"));
		
		logoffLink.click();
		
		//Esperando até que todos os elementos da página seguinte sejam carregadas
		this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//Validando o teste
		String validacao = this.driver.findElement(By.id("linkLogin")).getText();
		assertEquals("Login", validacao);
	}
}
