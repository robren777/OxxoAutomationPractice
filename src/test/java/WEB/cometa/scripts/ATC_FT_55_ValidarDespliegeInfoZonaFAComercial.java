package WEB.cometa.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**Desc: Validar que los registros mostrados correspondan a la Zona y FA designadas al perfil.
* TC-FT-55- E6 - Validar despliegue de informaciÃ³n por Zona y FA â€“ Comercial.
* @author Jorge Hernandez
* @date Junio 2024
*/
public class ATC_FT_55_ValidarDespliegeInfoZonaFAComercial extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_55_ValidarDespliegeInfoZonaFAComercial_test(HashMap<String, String> data) throws Exception{
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
		 } else {
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
		 addStep("Validar fuente de abastos y zona coincidan con la designada al perfil");
		 boolean resultStep5 = pc.validacionFAYZona(6);// numero para retroceder esa cantidad de meses, actualmente no hay registros mas cercanos
		 if(resultStep5){
			 testCase.addTextEvidenceCurrentStep("Se pudo validar la fuente de abastos");
		 } else {
			 testCase.addTextEvidenceCurrentStep("No se pudo validar la fuente de abastos");
		 }
		 assertTrue(resultStep5, "No se pudo validar la fuente de abastos");

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
		return "Validar que los registros mostrados correspondan a la Zona y FA designadas \n" +
				"al perfil";
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
