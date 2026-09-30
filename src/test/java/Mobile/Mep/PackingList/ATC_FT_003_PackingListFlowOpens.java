package Mobile.Mep.PackingList;
import org.openqa.selenium.By;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.AutomationName;
import modelo.TestCase;
import utils.AppiumUtil;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.time.Duration;
import java.util.HashMap;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import Mobile.Mep.PackingList.om.PackingListUtils;
import Mobile.Mep.PackingList.om.VistaCajaPromOxxoPackingList;
import Mobile.Mep.om.VistaPanelDeControl;
import Mobile.util.GlobalVariables;
import io.appium.java_client.AppiumDriver;
import modelo.BaseExecution;
import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Check that "Caja PromOXXO - Packing List" screen is opened when user clicks on the "Caja PromOXXO - Packing List" option on the "Dashboard" screen
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC-PL-003-HH_TABLET_PL_PackingList_flow_opens
 *
 * PREREQUISITOS: 1. User must be registered in the system 2. To have the Cr codes of the store: CrPlace: 10MON, CrStore: 50SHC 3. User is on the "Dashboard" screen 4. Period is in N-5 to N+1 days (N=start of period)
 *
 * INSUMOS:
 *
 * @author GTIM
 * @date 09/07/2026
 */
public class ATC_FT_003_PackingListFlowOpens extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_003_PackingListFlowOpens_test(HashMap<String, String> data) throws Exception {
        /**
         * ******************************************************************************
         *  Inicialización/Declaración de utilerías
         * ********************************************************************************
         */
    	AppiumUtil au = null;
    	String appPackage;
        testCase.setProject_Name("Mobile Mep --> PromOxxo Packing List");
		appPackage = data.get("appPackage");
	String appActivity = data.get("appActivity");
	//Capabilities
	String url = GlobalVariables.urlMep;
	DesiredCapabilities cap = new DesiredCapabilities();
	cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
	cap.setCapability("deviceName", data.get("deviceName"));
	cap.setCapability("platformVersion", data.get("platformVersion"));
	cap.setCapability("platformName", data.get("platformName"));
	cap.setCapability("appPackage", data.get("appPackage"));
	cap.setCapability("appActivity", data.get("appActivity"));
	// Deshabilitar autollenado
	cap.setCapability("appium:disableAutofill", true);
	cap.setCapability("appium:autoGrantPermissions", true);
	// Deshabilitar teclado automático
	cap.setCapability("unicodeKeyboard", true);
	cap.setCapability("resetKeyboard", true);

	// Utilerias e Inicialización del Driver
	try {
	    au = new AppiumUtil(url, cap);
	} catch (SessionNotCreatedException e) {
	    assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
	    e.printStackTrace();
	}
	au.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Esperar 10 Segundos por cada elemento
									      // que falte por aparecer
	PackingListUtils utils = new PackingListUtils(testCase, au, this);
	VistaPanelDeControl vistaPanelDeControl = new VistaPanelDeControl(testCase, au, this);
	VistaCajaPromOxxoPackingList vistaCajaPromOxxoPackingList = new VistaCajaPromOxxoPackingList(testCase, au, this);
	au.driver.activateApp(appPackage + "/" + appActivity);

	/**
	 * Pre-requisitos: 3. User is on the "Dashboard" screen
	 */
	addStep("Se ejecuta el pre-requisto 3. User is on the \"Dashboard\" screen ", "Debe ser ejecutado con exito");
	utils.logingAndGoToDashboardScreen(data.get("crPlaza"), data.get("crTienda"), data.get("numEmpleado"));
        /**
         * ******************************************************************************
         *  Paso 1
         * ********************************************************************************
         */
        addStep("Click on the 'Caja PromOXXO - Packing List' option","Validate that 'Caja PromOXXO - Packing List' screen is opened with all expected elements");
        vistaPanelDeControl.clickOpcionCajaPromPackingList();
        assertTrue(vistaCajaPromOxxoPackingList.validateIfBtnBackArrowIsDisplayed(), "No se encontró la opción back arrow button.");
        assertTrue(vistaCajaPromOxxoPackingList.validateIfTitleIsDisplayed(), "No se encontró el titulo.");
        assertTrue(vistaCajaPromOxxoPackingList.validateIfBtnQuestionMarkIsDisplayed(), "No se encontró el simbolo de pregunta.");
        assertTrue(vistaCajaPromOxxoPackingList.validateIfSubtitleIsDisplayed(), "No se encontró el subtitulo.");
        assertTrue(vistaCajaPromOxxoPackingList.validateIfBtnRealizarPackingListIsDisplayed(), "No se encontró el boton de realizar packing list.");
        assertTrue(vistaCajaPromOxxoPackingList.validateIfBtnCajaCompletaIsDisplayed(), "No se encontró el boton de caja completa.");
        
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        // Mismo que PREREQUISITOS en documentación
        return "1. User must be registered in the system 2. To have the Cr codes of the store: CrPlace: 10MON, CrStore: 50SHC 3. User is on the \"Dashboard\" screen 4. Period is in N-5 to N+1 days (N=start of period)";
    }

    @Override
    public String setTestDescription() {
        // Mismo que DESCRIPCIÓN en documentación
        return "Check that \"Caja PromOXXO - Packing List\" screen is opened when user clicks on the \"Caja PromOXXO - Packing List\" option on the \"Dashboard\" screen";
    }

    @Override
    public String setTestDesigner() {
        // Mismo que @author
        return  "Mariana Gabriela Avitia Maturino";
    }

    @Override
    public String setTestFullName() {
        // Mismo nombre de la clase
        return "ATC_FT_003_PackingListFlowOpens";
    }

    @Override
    public String setTestInstanceID() {
        // Dejar null
        return null;
    }
}
