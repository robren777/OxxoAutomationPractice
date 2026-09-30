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
 * DESCRIPCIÓN: Check that Report was sent when user clicks on the "Continuar" button in the "Packing list" flow
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC-PL-026-HH_TABLET_PL_ReportList_Continuar_button_sends_report
 *
 * PREREQUISITOS: 1. User must be registered in the system 2. To have the Cr codes of the store: CrPlace: 10MON, CrStore: 50SHC 3. Period is in N-5 to N+1 days (N=start of period) 4. User is on the "Reporte de Materiales Faltantes" screen
 *
 * INSUMOS:
 *
 * @author Mariana Avitia
 * @date 09/07/2026
 */
public class ATC_FT_026_ReportListContinuarButtonSendsReport extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_026_ReportListContinuarButtonSendsReport_test(HashMap<String, String> data) throws Exception {
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
	addStep("Se ejecuta el pre-requisto 3. User is on the \"Reporte de Materiales Faltantes\" screen ", "Debe ser ejecutado con exito");
	utils.logingAndGoToDashboardScreen(data.get("crPlaza"), data.get("crTienda"), data.get("numEmpleado"));
	vistaPanelDeControl.clickOpcionCajaPromPackingList();
	vistaCajaPromOxxoPackingList.clickBtnRealizarPackingList();
	vistaCajaPromOxxoPackingList.clickBtnCamaraAntesDeUnaFoto();
	vistaCajaPromOxxoPackingList.clickBtnFlechaDerecha();
        vistaCajaPromOxxoPackingList.clickBtnContinuar();

        /**
         * ******************************************************************************
         *  Paso 1
         * ********************************************************************************
         */
        addStep("Click on the checkbox near any of the promo materials","\"Continuar\" button should be enabled");
        vistaCajaPromOxxoPackingList.clickCheckBoxOne();
	assertTrue(vistaCajaPromOxxoPackingList.validateIfBtnContinuarIsEnabled(),"No se muestra el boton continuar enabled");
        /**
         * ******************************************************************************
         *  Paso 2
         * ********************************************************************************
         */
        addStep("Click on the \"Continuar\" button"," - \"Success\" popup should be shown\r\n"
        	+ "- User should be redirected to the \"Caja PromOXXO - Packing List\" screen\r\n"
        	+ "- Report should be shown in the \"Todos los reportes\" screen");
        vistaCajaPromOxxoPackingList.clickBtnContinuar();
        assertTrue(vistaCajaPromOxxoPackingList.validateIfPopUpEnvioExitosoIsDisplayed(),"No se muestra el pop up.");
	assertTrue(vistaCajaPromOxxoPackingList.validateIfTitlePackingListIsDisplayed(),"No se muestra la pantalla packing list.");
	vistaCajaPromOxxoPackingList.clickBtnVerReportes();
	assertTrue(vistaCajaPromOxxoPackingList.validateIfExpandFirstReportIsDisplayed(),"No se muestra el reporte creado.");
	vistaCajaPromOxxoPackingList.clickExpandFirstReport();
	assertTrue(vistaCajaPromOxxoPackingList.validateIfDescriptionReportLineOneIsDisplayed(),"No se muestra la descripcion del primer reporte");
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        // Mismo que PREREQUISITOS en documentación
        return "1. User must be registered in the system 2. To have the Cr codes of the store: CrPlace: 10MON, CrStore: 50SHC 3. Period is in N-5 to N+1 days (N=start of period) 4. User is on the \"Reporte de Materiales Faltantes\" screen";
    }

    @Override
    public String setTestDescription() {
        // Mismo que DESCRIPCIÓN en documentación
        return "Check that Report was sent when user clicks on the \"Continuar\" button in the \"Packing list\" flow";
    }

    @Override
    public String setTestDesigner() {
        // Mismo que @author
        return "Mariana Avitia";
    }

    @Override
    public String setTestFullName() {
        // Mismo nombre de la clase
        return "ATC_FT_026_ReportListContinuarButtonSendsReport";
    }

    @Override
    public String setTestInstanceID() {
        // Dejar null
        return null;
    }
}
