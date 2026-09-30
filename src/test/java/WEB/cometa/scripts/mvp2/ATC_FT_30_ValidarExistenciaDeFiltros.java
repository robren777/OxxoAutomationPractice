package WEB.cometa.scripts.mvp2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que los registros mostrados coincidan con las caracteristicas especificadas en el filtro seleccionado
* MTC-FT-30- E6- Validar FILTROS
* @author Yair Lizarraga
* @date Junio 2024
*/
public class ATC_FT_30_ValidarExistenciaDeFiltros extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_30_ValidarExistenciaDeFiltros_test (HashMap<String, String> data) throws Exception {
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
		addStep("Validar los filtros existentes"); 
		boolean validarFiltrosDirectos = pc.validarFiltrosDirectos();
		if(validarFiltrosDirectos)
			testCase.addTextEvidenceCurrentStep("El Menu es correcto");
		else
			testCase.addTextEvidenceCurrentStep("El Menu no es correcto");
		assertTrue(validarFiltrosDirectos, "El Menu es correcto");
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
		return "Validar que existan en las pantallas los siguientes filtros para las bÃºsquedas:\r\n"
		+ "1. Proveedor\r\n"
		+ "2.RFC\r\n"
		+ "3. Zona\r\n"
		+ "4. RegiÃ³n\r\n"
		+ "5. CR Plaza \r\n"
		+ "6. Plaza\r\n"
		+ "7. CR Tienda\r\n"
		+ "8. Tienda\r\n"
		+ "9. FA (Fuente de Abasto)\r\n"
		+ "10. Calendario para seleccionar las fechas en espaÃ±ol";
}

	@Override
	public String setTestDesigner() {
		return"Yair Lizarraga";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_30_ValidarExistenciaDeFiltros_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@AfterMethod
	public void afterMethod(){
		u.getDriver().quit();
	}
}
