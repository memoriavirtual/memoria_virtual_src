package test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Teste do caso de uso "Realizar Logoff". Herda a classe TesteLogin.
 * 
 * @author Fernando
 *
 */
public class TesteCadastrarInstituicao extends TesteLogin {

	private WebElement cadastrarInstituicao;
	
	private WebElement nomeTxt;
	private WebElement municipioTxt;
	private WebElement localidadeTxt;
	private WebElement estadoTxt;
	private WebElement paisTxt;
	private WebElement enderecoTxt;
	private WebElement cepTxt;
	private WebElement caixaPostalTxt;
	private WebElement telefoneTxt;
	private WebElement emailTxt;
	private WebElement urlTxt;
	private WebElement propIdTxt;
	private WebElement adminPropTxt;
	private WebElement latitudeTxt;
	private WebElement longitudeTxt;
	private WebElement altitudeTxt;
	private WebElement legislacaoTxt;
	private WebElement sinteseHistoricaTxt;
	
	private WebElement cadastrarBtn;
	
	private WebElement finishBtn;

	/**
	 * Método construtor que chama o construtor da classe-mãe.
	 */
	public TesteCadastrarInstituicao() {
		super();
	}

	public void setPaginaCadastro() {
		this.cadastrarInstituicao = driver.findElement(By.id("j_idt30:menuCadastrarInstituicao"));
	}
	
	/**
	 * Método que identifica os elementos essenciais da página de cadastro.
	 */
	public void setElementosCadastro() {
		//Acha
		this.nomeTxt = driver.findElement(By.id("nome"));
		this.municipioTxt = driver.findElement(By.id("cidade"));
		this.localidadeTxt = driver.findElement(By.id("localidade"));
		this.estadoTxt = driver.findElement(By.id("estado"));
		this.paisTxt = driver.findElement(By.id("pais"));
		this.enderecoTxt = driver.findElement(By.id("endereco"));
		this.cepTxt = driver.findElement(By.id("cep"));
		this.caixaPostalTxt = driver.findElement(By.id("caixa-postal"));
		this.telefoneTxt = driver.findElement(By.id("telefone"));
		this.emailTxt = driver.findElement(By.id("email"));
		this.urlTxt = driver.findElement(By.id("url"));
		this.propIdTxt = driver.findElement(By.id("identificacaoProprietario"));
		this.adminPropTxt = driver.findElement(By.id("administradorPropriedade"));
		this.latitudeTxt = driver.findElement(By.id("latitude"));
		this.longitudeTxt = driver.findElement(By.id("longitude"));
		this.altitudeTxt = driver.findElement(By.id("altitude"));
		this.legislacaoTxt = driver.findElement(By.id("legislacao"));
		this.sinteseHistoricaTxt = driver.findElement(By.id("sintese-historica"));
		this.cadastrarBtn = driver.findElement(By.id("cadastrar"));
		
		//cadastra
		this.nomeTxt.sendKeys("Uni Teste");
		this.municipioTxt.sendKeys("Sao Carlos");
		this.localidadeTxt.sendKeys("Logo ali");
		this.estadoTxt.sendKeys("SP");
		this.paisTxt.sendKeys("Brasil");
		this.enderecoTxt.sendKeys("Rua da Goiaba, 23456");
		this.cepTxt.sendKeys("12345-678");
		this.caixaPostalTxt.sendKeys("12345-678");
		this.telefoneTxt.sendKeys("(16)99999-9999");
		this.emailTxt.sendKeys("uniteste@uniteste.com.br");
		this.urlTxt.sendKeys("uniteste.com.br");
		this.propIdTxt.sendKeys("1234567890");
		this.adminPropTxt.sendKeys("Admin Propriedade");
		this.latitudeTxt.sendKeys("1");
		this.longitudeTxt.sendKeys("1");
		this.altitudeTxt.sendKeys("1");
		this.legislacaoTxt.sendKeys("Lei da Uni Teste");
		this.sinteseHistoricaTxt.sendKeys("Loren ipsum");
		
	}

	public void setElementosFinalizarCadastro() {
		this.finishBtn = driver.findElement(By.id("finish-button"));
	}
	
	/**
	 * Fluxo principal do caso de uso "Realizar Login".
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void cadastrarInstituicao() throws InterruptedException {
		//Faz o login
		this.loginFluxoPrincipal();

		// Esperando até que todos os elementos da página seguinte sejam carregadas
		this.driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

		//Vai para a página de cadastro
		this.setPaginaCadastro();
		this.cadastrarInstituicao.click();
		
		// Esperando até que todos os elementos da página seguinte sejam carregadas
		this.driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

		//Preenche o cadastro e envia o form
		this.setElementosCadastro();
		this.cadastrarBtn.click();
		
		// Esperando até que todos os elementos da página seguinte sejam carregadas
		this.driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		
		//Finaliza o cadastro, sem arquivo
		this.setElementosFinalizarCadastro();
		this.finishBtn.click();

		// Validando o teste
		String classes = this.driver.findElement(By.id("mensagens")).getAttribute("class");
		String validacao = "";
		for (String c : classes.split(" ")) {
			if (c.contentEquals("alert-success")) validacao = "alert-success";
		}
		assertEquals("alert-success", validacao);
	}
}
