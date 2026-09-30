package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que los PDF dentro del zip su nombre se compone de la siguiente manera \"Verificación_fecha en español y la fecha debe de corresponder a el campo\"Fecha\" que se encuentra dentro del PDF"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_055_Validar que PDF descargado constenga los datos correctos
*
*PREREQUISITOS:
*1. Credenciales para ingresar al Portal cometa
*2.Liga de Portal Cometa
*
*INSUMOS: 
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
*
* @author Alejandro Valdez
* @date Julio 2025
*/
public class ATC_FT_055_ValidarPDFcontieneDatosCorrectos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_055_ValidarPDFcontieneDatosCorrectos_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		/*
		 * Paso 1 - Contar con el archivo zip descargado de comprobante de compras
		 */
		addStep("Contar con el archivo zip descargado de comprobante de compras");
		boolean validacion = pc.validarDescargaComprobanteMasivaPDF();
		assertTrue(validacion,"No se descargo correctamente");
		
		/*
		 * Paso 2 - Extraer el archivo PDF del zip
		 */
		addStep("Extraer el archivo PDF del zip");
		validacion = pc.extraerZipSimple(true);
		assertTrue(validacion,"No se pudo realizar de manera correcta"); 
		
		/*
		 * Paso 3 - Validar que el nombre del PDF cumpla con los criterios del nombre
		 */
		addStep("Validar que el nombre del PDF cumpla con los criterios del nombre");
		validacion = pc.validarformatoNombrePdfD();
		assertTrue(validacion,"No se pudo realizar de manera correcta");
		

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
		return "1. Credenciales para ingresar al Portal cometa"
				+ "2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que los PDF dentro del zip su nombre se compone de la siguiente manera \"Verificación_fecha en español y la fecha debe de corresponder a el campo\"Fecha\" que se encuentra dentro del PDF\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_055_ValidarPDFcontieneDatosCorrectos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}