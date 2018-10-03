package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Primeiro teste com Selenium no projeto MVirtual.
 * Implementa um teste de login automatizado.
 * @author Fernando e Jayro
 *
 */
public class TesteLoginMemoriaVirtual {
	
	protected WebDriver driver;
	private WebElement searchLoginTxt;
	private WebElement searchPasswordTxt;
	private WebElement searchLoginBtn;

	
	/**
	 * Método construtor da classe LoginMemoriaVirtual. 
	 * Aqui, a ideia será definir o atributo 'driver' de acordo com as propriedades
	 * definidas no arquivo 'config.properties'. Por enquanto, estamos testando somente
	 * no Chrome, então só precisa definir o caminho do driver no arquivo das propriedades.
	 * Isso será feito posteriormente.
	 */
	public TesteLoginMemoriaVirtual(){
		//"setando o caminho do webdriver do chrome
		System.setProperty("webdriver.chrome.driver", "/home/fernando/git/memoria_virtual_src/trunk/memoriaVirtualTesteAutomatizado/lib/seleniumjars/chromedriver");
		
		//abre uma nova classe controladora do webdriver do Chrome		
		this.driver = new ChromeDriver();
	}
	
	public void setElementosDaPagina(){
		this.searchLoginTxt = driver.findElement(By.name("j_idt9:usuario"));
		this.searchPasswordTxt = driver.findElement(By.name("j_idt9:senha"));
		this.searchLoginBtn = driver.findElement(By.name("j_idt9:j_idt15"));
	}
	
	/**
	 * Método base de teste. A maioria das interações com o sistema passa pelo login
	 * do usuário.
	 * @param login
	 * @param senha
	 * @throws InterruptedException
	 */
	public void testeBase (String login, String senha) throws InterruptedException{				
		//this.abrirPaginaLogin();
		
		//enviando as mensagens desejadas para as txt's
		this.searchLoginTxt.sendKeys(login);		
		this.searchPasswordTxt.sendKeys(senha);
		Thread.sleep(1000); //dando 1 segundo(s) para o usuário ver o teste
		this.searchLoginBtn.click();
		
		Thread.sleep(2000);	//dando 2 segundo(s) para o usuário ver o teste
		
		this.encerrarPagina();
	}
	
	/**
	 * 
	 * @throws InterruptedException 
	 */
	public void testeFluxoAlternativo1() throws InterruptedException{
		//this.abrirPaginaLogin();
				
		//enviando as mensagens desejadas para as txt's
		this.searchLoginTxt.sendKeys("mvirtual");		
		this.searchPasswordTxt.sendKeys("errado");
		Thread.sleep(1000); //dando 1 segundo(s) para o usuário ver o teste
		this.searchLoginBtn.click();
		
		Thread.sleep(500);
		
		//volta para o fluxo principal
		this.setElementosDaPagina();
		this.searchLoginTxt.sendKeys("mvirtual");		
		this.searchPasswordTxt.sendKeys("mvirtual");
		this.searchLoginBtn.click();

		Thread.sleep(2000);

		
		this.encerrarPagina();
	}
	
	
	public void testeFluxoAlternativo2() throws InterruptedException{
		//identificando elementos da página aberta
		for (int i = 1; i <= 3; i++){
			this.testeFluxoAlternativo1();
			
			this.searchLoginTxt = driver.findElement(By.name("j_idt9:usuario"));
			this.searchPasswordTxt = driver.findElement(By.name("j_idt9:senha"));
			this.searchLoginBtn = driver.findElement(By.name("j_idt9:j_idt15"));
		}
			
		//procurando os elementos do recaptcha
		WebElement searchRecaptchaTxt = driver.findElement(By.name("recaptcha-anchor-label"));
		
	}
	
	/**
	 * 
	 */
	public void abrirPaginaLogin(){
		//abre o site desejado através do webdriver
		driver.get("http://localhost:8080/memoriavirtual/login.jsf");
	}
	
	/**
	 * 
	 */
	public void encerrarPagina(){
		driver.quit();
	}
}
