package WEB.updateNomina.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CNT {
	By BtnActualizar = By.xpath("//input[@value=\"Actualizar\"]");
	
    By selectColonia = By.xpath("//select[@name='p_id_colonia']");
    By optionColonia2 = By.xpath("/html/body/form[1]/table/tbody/tr[6]/td[2]/select/option[2]");
    By Telefono = By.xpath("//input[@name='p_telefono']");


    By Sintel = By.xpath("//input[@name='p_tel_no_disponible']");
    By p_alternativa_tel= By.xpath("//select[@name='p_alternativa_tel']");
    By p_especifique=By.xpath("//input[@name='p_especifique']");

    private SeleniumUtil u;
    private WebDriverWait wait;
    private TestCase testCase;
    private UpdateNomina updNom;
    public CNT(SeleniumUtil u, TestCase testCase) {
        super(); // Llama al constructor de la clase padre (en este caso, Object)
        this.u = u; // Asigna el objeto SeleniumUtil pasado como par?metro a la variable de instancia
        this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60)); // Inicializa WebDriverWait con el driver de SeleniumUtil y un timeout de 60 segundos
        this.testCase = testCase; // Asigna el objeto TestCase pasado como par?metro a la variable de instancia
        this.updNom = new UpdateNomina(u, testCase);
    }

    public void capturarColonia(){
        wait.until(ExpectedConditions.elementToBeClickable(selectColonia));
        System.out.println("Se encontr� select colonia");
        u.click(selectColonia);
        wait.until(ExpectedConditions.elementToBeClickable(optionColonia2));
        System.out.println("Se encontr� option 2 para colonia");
        //u.click(optionColonia2);
        updNom.robotKeyDown(3);
    }
    
    public void capturarNumeroTel(String Tel, String No,String alternativa,String p_especifique1) {

        if (No.equalsIgnoreCase("NO")) {
            u.click(Sintel);

            u.hardWait(4);
            Select tama = new Select(u.getDriver().findElement(p_alternativa_tel));
            tama.selectByVisibleText(alternativa);
            u.highLight(p_alternativa_tel);
            u.clear(p_especifique);

            u.sendKeys(p_especifique, p_especifique1);
            testCase.addScreenShotCurrentStep(u, "p_alternativa_tel");
        } else {
            u.highLight(Telefono);
            u.sendKeys(Telefono, Tel);
            testCase.addScreenShotCurrentStep(u, "telefono");}

    }
    public void clickBotonActualizar() {
    	MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(BtnActualizar);
		u.highLight(BtnActualizar);
		testCase.addScreenShotCurrentStep(u, "DatosOpAC");
		u.click(BtnActualizar);

		wait.until(ExpectedConditions.alertIsPresent());
		System.out.println("Se muestra alerta");

		u.getDriver().switchTo().alert().accept();
		System.out.println("Se acepta alerta");
    }
}
