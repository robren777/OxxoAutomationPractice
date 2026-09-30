package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que exista una opciÃ³n visible de manera universal dentro del portal
 * que me permita redirigirme a la pantalla principal para el perfil de Directos.
 * MTC-FT-63- E6- Validar menÃº de inicio en perfil Directos â€“ Comercial.
 * @author Jorge Hernandez
 * @date Junio 2024
*/
public class ATC_FT_63_ValidarMenuInicioPerfilDirectos extends BaseExecution{

    SeleniumUtil u;

    @Test(dataProvider = "data-provider")
    public void ATC_FT_63_ValidarMenuInicioPerfilDirectos_test(HashMap<String, String> data) throws Exception{
        testCase.setProject_Name("Portal Web: Cometa");

        /*
		 * Variables
		 ******************************************************************************************/
        String user = data.get("user");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String url = data.get("Url");

        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometa pc = new PortalCometa(u, testCase);

        /*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar a la liga");
		u.get(url);
		u.refresh();
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "Pantalla cargada");
		pc.PrivatePortal();

        /*
		 *                                             PASO 2
		 ******************************************************************************************/
        addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion");
		boolean resultStep2 = pc.IniciarSesionNoToken(user, password);
		if(resultStep2){
			testCase.addTextEvidenceCurrentStep("Se logro iniciar sesion correctamente");
		} else {
			testCase.addTextEvidenceCurrentStep("No se pudo iniciar sesion");
		}
		assertTrue(resultStep2, "No se pudo iniciar sesion");
        
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Ingresar a Portal Cometa");
		boolean resultStep3 = pc.seleccionarPortalCometaQA();
		if(resultStep3){
			testCase.addTextEvidenceCurrentStep("Se pudo seleccionar el portal Cometa");
		} else{
			testCase.addTextEvidenceCurrentStep("No es visible el elemento de Cometa");
		}
		assertTrue(resultStep3, "No es visible el elemento de Cometa");

        /*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Ingresar a Comprobantes de compra y Resumen");
		boolean resultStep4 = pc.clicBotonCometaDirectos();
		if(resultStep4){
			testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Directos");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el elemento Directos");
		}
		assertTrue(resultStep4, "No se encontro el elemento Directos");

		boolean resultStep4_1 = pc.ClicBotonComprobantesDeCompra();
		if(resultStep4_1){
			testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Comprobantes de Compra");
		} else {
			testCase.addTextEvidenceCurrentStep("No se encontro el elemento Comprobantes de Compra");
		}
		assertTrue(resultStep4_1,"No se encontro el elemento Comprobantes de Compra");

        /*
		 *                                             PASO 5
		 ******************************************************************************************/
        addStep("Validar visualizacion de boton de inicio y dar click");
        boolean resultStep5 = pc.validacionBotonInicioCP();
		if(resultStep5){
			testCase.addTextEvidenceCurrentStep("Se valida que el boton de inicio es visible");
		} else {
			testCase.addTextEvidenceCurrentStep("No se muestra el boton de inico");
		}
		assertTrue(resultStep5, "No se muestra el boton de inico");

        /*
		 *                                             PASO 6
		 ******************************************************************************************/
        addStep("Seleccionar boton Reportes de Home");
        boolean resultStep6 = pc.ClickBotonNavDirectosReportes();
		if(resultStep6){
			testCase.addTextEvidenceCurrentStep("Se valida que el Boton de Home se muestra en Reportes");
		} else {
			testCase.addTextEvidenceCurrentStep("No se muestra el boton de Home en Reportes");
		}
		assertTrue(resultStep6, "No se muestra el boton de Home en Reportes");

        boolean resultStep6_1 = pc.validacionMensajeReporteDirectos();
		if(resultStep6_1){
			testCase.addTextEvidenceCurrentStep("Se muestra el mensaje en la pantalla de Directos");
		} else {
			testCase.addTextEvidenceCurrentStep("No se muestra el mensaje en la pantalla de Directos");
		}
		assertTrue(resultStep6_1, "No se muestra el mensaje en la pantalla de Directos");

         /*
		 *                                             PASO 7
		 ******************************************************************************************/
        addStep("Validar visualizacion de boton de inicio y dar click");
        boolean resultStep7 = pc.validacionBotonInicioCP();
		if(resultStep7){
			testCase.addTextEvidenceCurrentStep("Se valida que el boton de inicio es visible");
		} else {
			testCase.addTextEvidenceCurrentStep("No se muestra el boton de inico");
		}
		assertTrue(resultStep7, "No se muestra el boton de inico");

        u.quit();
    }

    @Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "1. Contar con credenciales vÃ¡lidas para el inicio de sesiÃ³n del perfil COMERCIAL \n" +
				"de Directos.";
	}

	@Override
	public String setTestDescription() {
		return "Validar que exista una opciÃ³n visible de manera universal dentro del portal \n" +
				"que me permita redirigirme a la pantalla principal para el perfil de Directos.";
	}

	@Override
	public String setTestDesigner() {
		return "Jorge Hernandez Sanchez";
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
