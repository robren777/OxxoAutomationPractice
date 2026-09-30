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
 * DESCRIPCIÓN: Check that "Caja Completa" flow is in the "Caja PromOXXO - Packing List" screen
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC-PL-027-HH_TABLET_PL_CajaCompleta_flow_exists
 *
 * PREREQUISITOS: 1. User must be registered in the system 2. To have the Cr codes of the store: CrPlace: 10MON, CrStore: 50SHC 3. Period is in N-5 to N+1 days (N=start of period) 4. User is on the "Caja PromOXXO - Packing list" screen
 *
 * INSUMOS:
 *
 * @author Mariana Avitia
 * @date 09/07/2026
 */
public class ATC_FT_027_CajaCompletaFlowExists extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_027_CajaCompletaFlowExists_test(HashMap<String, String> data) throws Exception {
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
	VistaCajaPromOxxoPackingList vistaCajaPromOxxoPackingList = new VistaCajaPromOxxoPackingList(testCase, au,this);
	au.driver.activateApp(appPackage + "/" + appActivity);

	/**
	 * Pre-requisitos: 3. User is on the "Photo Gallery" screen
	 */
	addStep("Se ejecuta el pre-requisto 2. User already positioned on the screen where 'Caja Completa' is available", "Debe ser ejecutado con exito");
	utils.logingAndGoToDashboardScreen(data.get("crPlaza"), data.get("crTienda"), data.get("numEmpleado"));
	vistaPanelDeControl.clickOpcionCajaPromPackingList();

        /**
         * ******************************************************************************
         *  Paso 1
         * ********************************************************************************
         */
        addStep("Click on the \"Caja Completa\" button","\"Reporte de caja Completa\" screen should be shown");
        vistaCajaPromOxxoPackingList.clickBtnCajaCompleta();
	assertTrue(vistaCajaPromOxxoPackingList.validateIfTitleCajaCompletaIsDisplayed(),"No se muestra la pantalla caja completa");
        /**
         * ******************************************************************************
         *  Paso 2
         * ********************************************************************************
         */
        addStep("Click on the \"Selecciona un motivo\"","List of available reasons to report should be expanded");
        vistaCajaPromOxxoPackingList.clickDropdownMotivo();
        assertTrue(vistaCajaPromOxxoPackingList.validateIfOpcionNoRecibiIsDisplayed(),"No se muestra la lista de opciones");
        /**
         * ******************************************************************************
         *  Paso 3
         * ********************************************************************************
         */
        addStep("Choose any reason","Reason should be selected");
        vistaCajaPromOxxoPackingList.clickOpcionNoRecibi();
	assertTrue(vistaCajaPromOxxoPackingList.validateIfBtnFinalizarIsEnabled(),"No se muestra el boton finalizar disponible");
        /**
         * ******************************************************************************
         *  Paso 4
         * ********************************************************************************
         */
        addStep("Click on the \"Finalizar\" button","\"Caja Completa\" report should be created");
        vistaCajaPromOxxoPackingList.clickBtnFinalizar();
        assertTrue(vistaCajaPromOxxoPackingList.validateIfPopUpEnvioExitosoCajaIsDisplayed(),"No se muestra el pop up.");
	
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_027_CajaCompletaFlowExists";
    }

    @Override
    public String setTestDescription() {
        return "Validate that a 'Caja Completa' report can be created successfully.";
    }

    @Override
    public String setTestDesigner() {
        return "Mariana Avitia";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "1. Application installed\n2. User already positioned on the screen where 'Caja Completa' is available";
    }

    @Override
    public void beforeTest() {
        // Optional
    }


}
