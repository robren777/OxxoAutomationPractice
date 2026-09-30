package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que los registros mostrados coincidan con las caracterÃ­sticas especificadas en el filtro seleccionado.
* MTC-FT-31 -E6- Validar la funcionalidad de filtrado en Comprobantes de Compra Resumen
* @author Yair Lizarraga
* @date Junio 2024
*/
public class ATC_FT_40_ValidarComprobantesPlaza extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_40_ValidarComprobantesPlaza_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		String password = data.get("password");
		String url = GlobalVariables.URL_PORTAL_APPS;
		String proveedor = data.get("proveedor");
		
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometa pc = new PortalCometa(u, testCase); // crea objeto de locator
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar las credenciales correspondientes al perfil Administrativo-Directos."); 
		u.get(data.get("Url"));
		u.hardWait(3);
		pc.PrivatePortal(); 
		pc.IniciarSesion(user, password);
		pc.ClicPortalCometa();
		
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar a la pÃ¡gina de Comprobantes de Compra y Resumen.\r\n"); 
		pc.ClicBotonDirectos();
		
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Validar que los comprobantes en mostrados coincidan con la plaza asignada al perfil"); 
		boolean validarExistenciaBtns = pc.ClicBotonComprobantesDeCompra();
		if(validarExistenciaBtns)
			testCase.addTextEvidenceCurrentStep("Los botones existen");
		else
			testCase.addTextEvidenceCurrentStep("Los botones no existen");
		assertTrue(validarExistenciaBtns, "Se muestra la informaciÃ³n esperada");
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		return"Yair Lizarraga";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_40_ValidarComprobantesPlaza_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
