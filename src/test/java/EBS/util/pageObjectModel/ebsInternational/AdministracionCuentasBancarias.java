package EBS.util.pageObjectModel.ebsInternational;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class AdministracionCuentasBancarias {

    SeleniumUtil u;
    TestCase testCase;
    private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

    // Generación del constructor

    /**
     * @param u        Instancia de SeleniumUtil
     * @param testCase Instancia de TestCase
     * @author JesusH@hexaware.com
     */
    public AdministracionCuentasBancarias(SeleniumUtil u, TestCase testCase) {
        super();
        this.u = u;
        this.testCase = testCase;
        PageFactory.initElements(u.getDriver(), this);

    }

    //    Cuentas Bancarias

    //    Búsqueda simple
    @FindBy(id = "Go")
    public WebElement ir;
    @FindBy(id = "AccountNumber")
    public WebElement numeroDeCuenta;
    @FindBy(id = "AccountResultTable:ResultAcctName:0")
    public WebElement primerNombreCuenta;
    @FindBy(id = "AccountResultTable:singleSelectionID:0")
    public WebElement primerSelectCuenta;
    @FindBy(id = "UpdateAccountButton")
    public WebElement actualizarCuentas;
}
