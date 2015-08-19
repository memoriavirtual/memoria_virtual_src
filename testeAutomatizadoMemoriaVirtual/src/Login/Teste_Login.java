package Login;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class Teste_Login {
  public static WebDriver driver = null;
  public static void main(String[] args) {
System.setProperty("webdriver.chrome.driver", "/dev/chromedriver.exe"); //localiza o exe para rodar no chrome
WebDriver driver = new ChromeDriver();
driver.get("http://143.107.231.114:8080/memoriavirtual/login.jsf"); //abre o site do MV
try {
  Thread.sleep(1000);
} catch (InterruptedException e) {
// TODO Auto-generated catch block
  e.printStackTrace();
}  // Let the user actually see something!
///// TESTE LOGIN
WebElement conta = driver.findElement(By.name("j_idt9:j_idt11")); //localiza o nome do campo referente ao login
conta.sendKeys("mvirtual"); //escreve no campo login
WebElement senha = driver.findElement(By.name("j_idt9:j_idt15")); //localiza o nome do campo referente a senha
senha.sendKeys("mvirtual"); //escreve no campo senha
driver.findElement(By.name("j_idt9:j_idt17")).click(); //encontra o botão para logar e clica nele
try {
  Thread.sleep(1000);
} catch (InterruptedException e) {
// TODO Auto-generated catch block
  e.printStackTrace();
}  // Let the user actually see something!

driver.quit(); //encerra o programa
}
}
