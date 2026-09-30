package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc:Validar que exista un botÃ³n visible de manera universal dentro del perfil que permita realizar el reporte de una incidencia encontrada en el portal.
* MTC_FT_37_ValidarMenuInicioPerfilDirectos
* @author Yair Lizarraga
* @date Junio 2024
*/
public class ATC_FT_37_ValidarMenuInicioPerfilDirectos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_37_ValidarMenuInicioPerfilDirectos_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		String password = data.get("password");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometa pc = new PortalCometa(u, testCase); // crea objeto de locator
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil Oxxo-Plaza Directos"); 
		u.get(data.get("Url"));
		u.hardWait(3);
		pc.PrivatePortal(); 
		pc.IniciarSesion(user, password);
		pc.ClicPortalCometa();
		
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar a la pÃ¡gina de Comprobantes de Compra"); 
		pc.ClicBotonDirectos();
		pc.ClicBotonComprobantesDeCompra();
		
		
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

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con credenciales vÃ¡lidas para el inicio de sesiÃ³n del perfil Administrativo-Directos";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que exista una opciÃ³n visible de manera universal dentro del portal que me permita redirigirme a la pantalla principal para el perfil de Directos";
	}

	@Override
	public String setTestDesigner() {
		return"Yair Lizarraga";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_37_ValidarMenuInicioPerfilDirectos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}