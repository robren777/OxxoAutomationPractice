package Mobile.Mep.PackingList;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.Mep.PackingList.om.PackingListUtils;
import Mobile.Mep.PackingList.om.VistaCajaPromOxxoPackingList;
import Mobile.Mep.om.VistaPanelDeControl;
import Mobile.util.GlobalVariables;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

/**
 * DESCRIPCIÓN: Check that user is returned to the "Dashboard" screen when user
 * clicks on the "Back" arrow button on the "Caja PromOXXO - Packing List"
 * screen.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC-PL-004-HH_TABLET_PL_Back_button_opens_Dashboard_screen
 *
 * PREREQUISITOS: 1. User must be registered in the system 2. To have the Cr
 * codes of the store: CrPlace: 10MON, CrStore: 50SHC 3. Period is in N-5 to N+1
 * days (N=start of period) 4. User is on the "Caja PromOXXO - Packing list"
 * screen
 *
 * INSUMOS:
 *
 * @author Mariana Avitia
 * @date 09/07/2026
 */
public class ATC_FT_004_BackButtonOpensDashboardScreen extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_PL_004_BackButtonOpensDashboardScreen_test(HashMap<String, String> data) throws Exception {
	/**
	 * ******************************************************************************
	 * Inicialización/Declaración de utilerías
	 * ********************************************************************************
	 */
	AppiumUtil au = null;
	String appPackage;
	testCase.setProject_Name("Mobile Mep --> PromOxxo Packing List");
	appPackage = data.get("appPackage");
	String appActivity = data.get("appActivity");
	// Capabilities
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
	VistaCajaPromOxxoPackingList vistaCajaPromOxxoPackingList = new VistaCajaPromOxxoPackingList(testCase, au,
		this);
	au.driver.activateApp(appPackage + "/" + appActivity);

	/**
	 * Pre-requisitos: 3. User is on the "Dashboard" screen
	 */
	addStep("Se ejecuta el pre-requisto 3. User is on the \"Caja PromOXXO - Packing list\" screen ", "Debe ser ejecutado con exito");
	utils.logingAndGoToDashboardScreen(data.get("crPlaza"), data.get("crTienda"), data.get("numEmpleado"));
	vistaPanelDeControl.clickOpcionCajaPromPackingList();
	/**
	 * ******************************************************************************
	 * Paso 1
	 * ********************************************************************************
	 */
	addStep("Click on the 'Back' arrow button.", " 'Dashboard' screen should be opened");
	vistaCajaPromOxxoPackingList.clickBtnBackArrow();
	assertTrue(vistaPanelDeControl.validarOpcionCajaPromPackingList(), "No se encontró el dashboard");

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "1. User must be registered in the system 2. To have the Cr codes of the store: CrPlace: 10MON, CrStore: 50SHC 3. Period is in N-5 to N+1 days (N=start of period) 4. User is on the \"Caja PromOXXO - Packing list\" screen";
    }

    @Override
    public String setTestDescription() {
	return "Check that user is returned to the \"Dashboard\" screen when user clicks on the \"Back\" arrow button on the \"Caja PromOXXO - Packing List\" screen.";
    }

    @Override
    public String setTestDesigner() {
	return "Mariana Avitia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_004_BackButtonOpensDashboardScreen";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }
}
