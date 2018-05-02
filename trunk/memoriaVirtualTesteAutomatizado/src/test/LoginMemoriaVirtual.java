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
public class LoginMemoriaVirtual {
	
	public static void login (String login, String senha) throws InterruptedException{
		//"setando o caminho do webdriver do chrome
		System.setProperty("webdriver.chrome.driver", "/Dev/workspace/MemoriaVirtualTesteAutomatizado/lib/seleniumjars/chromedriver");
		
		//abre uma nova classe controladora do webdriver do Chrome
		WebDriver driver = new ChromeDriver();
		
		//abre o site desejado através do webdriver
		driver.get("http://localhost:8080/memoriavirtual/login.jsf");
		
		//identificando elementos da página aberta
		WebElement searchLoginTxt = driver.findElement(By.name("j_idt9:usuario"));
		WebElement searchPasswordTxt = driver.findElement(By.name("j_idt9:senha"));
		WebElement searchLoginBtn = driver.findElement(By.name("j_idt9:j_idt15"));
		
		//enviando as mensagens desejadas para as txt's
		searchLoginTxt.sendKeys(login);		
		searchPasswordTxt.sendKeys(senha);
		Thread.sleep(1000); //dando 1 segundo(s) para o usuário ver o teste
		searchLoginBtn.click();
		
		
		Thread.sleep(4000);	//dando 4 segundo(s) para o usuário ver o teste
		
		driver.quit();
	}
	
	/**
	 * Método principal do teste automatizado de login
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main (String []args) throws InterruptedException{
		login("mvirtual", "mvirtual");
	}
}
