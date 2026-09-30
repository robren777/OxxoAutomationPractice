package EBS.util.pageObjectModel.ebsInternational;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class ActualizarCuentaBancaria {

    SeleniumUtil u;
    TestCase testCase;
    private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

    // Generación del constructor

    /**
     * @param u        Instancia de SeleniumUtil
     * @param testCase Instancia de TestCase
     * @author JesusH@hexaware.com
     */
    public ActualizarCuentaBancaria(SeleniumUtil u, TestCase testCase) {
        super();
        this.u = u;
        this.testCase = testCase;
        PageFactory.initElements(u.getDriver(), this);

    }

    //    Actualizar cuentas bancarias
    @FindBy(xpath = "//a[@id='CE_ACCOUNT_INFO']")
    public WebElement informacionDeCuenta;

    public By habilitadaParaPagoEnKyribaWeb = By.id("BaDescFlex0__xc_");
    @FindBy(id = "BaDescFlex0__xc_")
    public WebElement habilitadaParaPagoEnKyriba;

    @FindBy(xpath = "//img[@title='Buscar: Habilitada para pago en Kyriba']")
    public WebElement buscarHabilitadaParaPagoEnKyriba;
}
