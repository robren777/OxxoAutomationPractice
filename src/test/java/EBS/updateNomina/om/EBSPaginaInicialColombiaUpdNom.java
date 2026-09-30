package EBS.updateNomina.om;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class EBSPaginaInicialColombiaUpdNom {
    //Elementos web pagina inicial
    By COOXXO_GLXXXXX_Maestro_CRs = By.xpath("//a[text()='COOXXO-GLXXXXX-Maestro CRs']");
    By Alta_de_Centros_de_Responsabilidad = By.xpath("//a[text()='Alta de Centros de Responsabilidad']");
    //By COOXXO_GLXXXXX_Maestro_CRs = By.id("AppsNavLink");

    SeleniumUtil u;
    TestCase testCase;
    private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

    // Generacion del constructor
    /**
     * @param u        Instancia de SeleniumUtil
     * @param testCase Instancia de TestCase
     * @author Mario Alberto De Le�n Rodr�guez
     */
    public EBSPaginaInicialColombiaUpdNom(SeleniumUtil u, TestCase testCase) {
        super();
        this.u = u;
        this.testCase = testCase;
        PageFactory.initElements(u.getDriver(), this);

    }

//    public By responsabilidad(String Responsabilidad) {
//        switch (Responsabilidad) {
//            case "COOXXO-GLXXXXX-Super Usuario":
//                return COOXXO_GLXXXXX_Maestro_CRs; //Regresa selector by.xpath()
//        }
//        return null;
//    }

    /**
     * Realiza un click en la responsabilidad especificada
     *
     * @author JesusH@hexaware.com
     */
    public void clicEnResponsabilidad() {
        u.waitForLoadPage();
        try {
            u.highLight(COOXXO_GLXXXXX_Maestro_CRs);
            u.click(COOXXO_GLXXXXX_Maestro_CRs);

            u.screenShotFull("click en la responsabilidad: " + COOXXO_GLXXXXX_Maestro_CRs);
            System.out.println("click en la responsabilidad: " + COOXXO_GLXXXXX_Maestro_CRs);
        } catch (Exception e) {
            logger.error("Error Responsabilidad no encontrada " + e);
            System.out.println("Error Responsabilidad no encontrada " + e);
        }

    }

    public void clicEnAltaDeCRs() {
        u.waitForLoadPage();

        WebDriver driver = u.getDriver();
        // Guarda el identificador de la ventana original
        String originalWindow = driver.getWindowHandle();
        try {

            u.highLight(Alta_de_Centros_de_Responsabilidad);
            u.click(Alta_de_Centros_de_Responsabilidad);
            u.screenShotFull("click en la responsabilidad: " + Alta_de_Centros_de_Responsabilidad);
            System.out.println("click en la responsabilidad: " + Alta_de_Centros_de_Responsabilidad);

            // Espera hasta que se abra una nueva ventana
            Set<String> allWindows = driver.getWindowHandles();
            for (String windowHandle : allWindows) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("Error Responsabilidad no encontrada " + e);
            System.out.println("Error Responsabilidad no encontrada " + e);
        }

    }

}
