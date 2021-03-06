package test;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Primeiro teste com Selenium no projeto MVirtual.
 * Implementa um teste de login automatizado.
 * @author Fernando e Jayro
 *
 */
public class TesteLogin {
	
	protected Properties prop;
	protected WebDriver driver;
	private WebElement searchLoginTxt;
	private WebElement searchPasswordTxt;
	private WebElement searchLoginBtn;
	

	
	/**
	 * Método construtor da classe TesteLogin. 
	 * Aqui, a ideia será definir o atributo 'driver' de acordo com as propriedades
	 * definidas no arquivo 'config.properties'. Por enquanto, estamos testando somente
	 * no Chrome, então só precisa definir o caminho do driver no arquivo das propriedades.
	 * Isso será feito posteriormente.
	 */
	public TesteLogin(){
		try {
			//arquivo de propriedades - contém propriedades dos webdrivers
			InputStream configs = new FileInputStream("webdriver.properties");
			
			//Opções do Google Chrome
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--no-sandbox");
			options.setBinary("/usr/bin/google-chrome-stable");
			
			//objeto de propriedades e carregamento do arquivo descrito acima
			this.prop = new Properties();
			this.prop.load(configs);
			
			//"setando o caminho do webdriver do chrome
			System.setProperty(prop.getProperty("webdriver.chrome"), prop.getProperty("webdriver.chromelocation"));
			
			//abre uma nova classe controladora do webdriver do Chrome		
			this.driver = new ChromeDriver(options);
		
		//Caso o arquivo de propriedades não seja achado
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo de propriedades não encontrado.");
			e.printStackTrace();
			
		//Caso haja alguma falha no carregamento do arquivo de propriedades
		} catch (IOException e) {
			System.out.println("Erro no carregamento do arquivo de propriedades.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que identifica os elementos essenciais da página de login.
	 */
	public void setElementosDaPagina(){
		this.searchLoginTxt = driver.findElement(By.id("formLogin:usuario"));
		this.searchPasswordTxt = driver.findElement(By.id("formLogin:senha"));
		this.searchLoginBtn = driver.findElement(By.id("formLogin:btnLogin"));
	}
	
	/**
	 * Método base de teste. A maioria das interações com o sistema passa pelo login
	 * do usuário.
	 * @throws InterruptedException
	 */
	@Test
	public void loginFluxoPrincipal () throws InterruptedException{				
		this.abrirPaginaLogin();
		this.setElementosDaPagina();
		
		String login = "mvirtual";
		String senha = "mvirtual";
		
		
		//enviando as mensagens desejadas para as txt's
		this.searchLoginTxt.sendKeys(login);		
		this.searchPasswordTxt.sendKeys(senha);
		//Thread.sleep(1000); //dando 1 segundo(s) para o usuário ver o teste
		this.searchLoginBtn.click();
		
		
		//Thread.sleep(1000);	//dando 1 segundo(s) para o usuário ver o teste
		
		//Esperando até que todos os elementos da página seguinte sejam carregadas
		this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//Validando o teste
		String validacao = this.driver.findElement(By.id("formCabecalho:txtUsuario")).getText();
		assertEquals("Memoria Virtual", validacao);
	}
	
	/**
	 * Fluxo alternativo 1 do caso de uso.
	 * @throws InterruptedException 
	 */
	@Test
	public void loginFluxoAlternativo1() throws InterruptedException{
		
		this.abrirPaginaLogin();
		this.setElementosDaPagina();
				
		//enviando as mensagens desejadas para as txt's
		this.searchLoginTxt.sendKeys("mvirtual");		
		this.searchPasswordTxt.sendKeys("errado");
		//Thread.sleep(1000); //dando 1 segundo(s) para o usuário ver o teste
		this.searchLoginBtn.click();
		
		//Procedimento correto
		this.setElementosDaPagina();
		this.searchLoginTxt.sendKeys("mvirtual");		
		this.searchPasswordTxt.sendKeys("mvirtual");
		//Thread.sleep(1000); //dando 1 segundo(s) para o usuário ver o teste
		this.searchLoginBtn.click();
		
		//Esperando até que todos os elementos da página seguinte sejam carregadas
		this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//Validando o teste
		String validacao = this.driver.findElement(By.id("formCabecalho:txtUsuario")).getText();
		assertEquals("Memoria Virtual", validacao);
		
	}
	
	
	public void loginFluxoAlternativo2() throws InterruptedException{
		this.abrirPaginaLogin();
		this.setElementosDaPagina();
		
		//Executa o fluxo alternativo 3 vezes
		for (int i = 1; i <= 3; i++){
			this.setElementosDaPagina();
			
			//enviando as mensagens desejadas para as txt's
			this.searchLoginTxt.sendKeys("mvirtual");		
			this.searchPasswordTxt.sendKeys("errado");
			//Thread.sleep(1000); //dando 1 segundo(s) para o usuário ver o teste
			this.searchLoginBtn.click();
			
		}	
	}
	
	/**
	 * Método que abre a página de login
	 */
	public void abrirPaginaLogin(){
		//abre o site desejado através do webdriver
		driver.get(prop.getProperty("website.loginpage"));
	}
	
	/**
	 * Método que fecha a janela de teste.
	 */
	public void encerrarPagina(){
		driver.quit();
	}
}
