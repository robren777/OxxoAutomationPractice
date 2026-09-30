package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import WEB.cometa.om.PortalCometaMVP4;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc:Validar que exista un botÃ³n visible de manera universal dentro del perfil que permita realizar el reporte de una incidencia encontrada en el portal.
* MTC-FT-46- E8- Validar botÃ³n de incidencia
* @author Yair Lizarraga
* @date Junio 2024
*/
public class ATC_FT_44_ValidarBotonInicio extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_44_ValidarBotonInicio_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		String password = data.get("password");
		String userNameAccess = data.get("userNameAccess");
		String passwordAccess = data.get("passwordAccess");
		String url = GlobalVariables.URL_PORTAL_APPS;
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc4 = new PortalCometaMVP4(u, testCase);
		PortalCometa pc = new PortalCometa(u, testCase); // crea objeto de locator
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil Oxxo-Plaza Directos"); 
		u.get(data.get("Url"));
		u.refresh();
		u.hardWait(3); // tiempo en segundos
		pc.IniciarSesion(user, password);
		pc.ClicPortalCometa();
		
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar a la pÃ¡gina de Comprobantes de Compra"); 
		pc.ClicBotonDirectos();
		pc.ClicBotonComprobantesDeCompra();
		pc4.MensajeEspereDesaparece(false);
		
		
		/*
		 *                                             PASO 3 
		 ******************************************************************************************/
		addStep("Validar la visualizaciÃ³n de un botÃ³n de inicio y dar click"); 
		pc.ClicBotonInicio();
		
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("SelecciÃ³n el botÃ³n de Reportes del menÃº home del MÃ³dulo"); 
		pc.ClicBotonDirectos();
		pc4.MensajeEspereDesaparece(false);
		pc.ClicBotonReportesDirecto();
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Validar la visualizaciÃ³n de un botÃ³n de inicio y dar click"); 
		boolean validarclicBotonInicio = pc.ClicBotonInicio();;
		if(validarclicBotonInicio)
			testCase.addTextEvidenceCurrentStep("El menu se muestra correctamente");
		else
			testCase.addTextEvidenceCurrentStep("El menu no se muestra correctamente");
		assertTrue(validarclicBotonInicio, "El menu se muestra correctamente");
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}
	
	@AfterMethod
    public void afterMethod(){
    	u.getDriver().quit();
    }

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con credenciales vÃ¡lidas para el inicio de sesiÃ³n del perfil Oxxo OS- Directos";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que exista un botÃ³n visible de manera universal dentro del perfil que permita regesar al inicio.";
	}

	@Override
	public String setTestDesigner() {
		return"Yair Lizaraga";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_44_ValidarBotonInicio_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}