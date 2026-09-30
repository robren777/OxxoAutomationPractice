package EBS.util.pageObjectModel.ebsInternational;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class BuscarSeleccionarHabilitadaParaPagoEnKyriba {
    SeleniumUtil u;
    TestCase testCase;

    // Generación del constructor

    /**
     * @param u        Instancia de SeleniumUtil
     * @param testCase Instancia de TestCase
     * @author JesusH@hexaware.com
     */
    public BuscarSeleccionarHabilitadaParaPagoEnKyriba(SeleniumUtil u, TestCase testCase) {
        super();
        this.u = u;
        this.testCase = testCase;
        PageFactory.initElements(u.getDriver(), this);

    }

    public By terminoBusquedaWeb = By.xpath("//input[@title='T�rmino de B�squeda']");
    @FindBy(xpath = "//input[@title='T�rmino de B�squeda']")
    public WebElement terminoDeBusqueda;

    @FindBy(xpath = "//button[@type='button'][contains(text(),'Ir')]")
    public WebElement irButton;

    public By yValueWeb = By.xpath("//span[@title='Campo Valor de Despliegue correspondiente a un valor de segmento flex'][contains(text(),'Y')]");
    @FindBy(xpath = "//span[@title='Campo Valor de Despliegue correspondiente a un valor de segmento flex'][contains(text(),'Y')]")
    public WebElement yValue;

    public By nValueWeb = By.xpath("//span[@title='Campo Valor de Despliegue correspondiente a un valor de segmento flex'][contains(text(),'N')]");
    @FindBy(xpath = "//span[@title='Campo Valor de Despliegue correspondiente a un valor de segmento flex'][contains(text(),'N')]")
    public WebElement nValue;
}
