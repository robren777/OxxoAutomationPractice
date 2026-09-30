package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que al  descargar el zip lo hacecon el nombre conformado \"Concentrado_fecha en español de la consulta inicial_fecha en español de la consulta final\""
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_054_Validar que se descarga correctamente archivo zip de PDF
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
public class ATC_FT_054_ValidarDescargaCorrectaArchivoZIPPDF extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_054_ValidarDescargaCorrectaArchivoZIPPDF_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		/*
		 * Paso 1 - Ingresar a la liga del portal con las con credenciales 
		 */
		addStep("Ingresar a la liga del portal con las con credenciales"); 
		u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
		boolean validacion = pc.loginScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		
		/*
		 * Paso 2 - Ingresar credenciales correspondientes al perfil de Cometa para inicio de sesion
		 */
		addStep("Ingresar credenciales correspondientes al perfil de Cometa para inicio de sesion");
		validacion = pc.iniciarSesion(user, password);
		assertTrue(validacion,"No se puede iniciar sesión de manera correcta");
		u.hardWait(10);
		validacion = pc.accessControlScreenSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 3 - Ingresar al portal Cometa desde access control
		 */
		addStep("Ingresar al portal Cometa desde access control");
		validacion = pc.ingPortalCometa();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		u.hardWait(8);
		validacion = pc.portalCometaScDirectosComercialSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 4 - Ingresar a comprobante de compras
		 */
		addStep("Ingresar a comprobante de compras");
		pc.opcDirectosPortalCometa(false);
		validacion = pc.opcComprobantesCompra(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		
		/*
		 * Paso 5 - Se visualiza correctamente pantalla de comprobante de compras.
		 */
		addStep("Se visualiza correctamente pantalla de comprobante de compras.");
		validacion = pc.comprobantesCompraCampos();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta"); 
		
		/*
		 * Paso 6 - Se realiza consulta y se da clic en el icono de PDF
		 */
		addStep("Se realiza consulta y se da clic en el icono de PDF");
		pc.busquedaFiltroPlazaFechaDirectos(user,true);
		validacion = pc.opcConsultar(false);
		assertTrue(validacion,"No se pudo seleccionar el botón correctamente"); 
		validacion = pc.selectIconPdf();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 7 - Validar que se descargue  archivo zip - validar que el archivo cumpla con los criterios de nombre.
		 */
		addStep("Validar que se descargue  archivo zip - validar que el archivo cumpla con los criterios de nombre.");
		u.hardWait(25);
		validacion = pc.validarDescargaComprobanteMasivaPDF();
		assertTrue(validacion,"No se descargo correctamente");
		

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
		return "Validar que al  descargar el zip lo hacecon el nombre conformado \"Concentrado_fecha en español de la consulta inicial_fecha en español de la consulta final\"\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_054_ValidarDescargaCorrectaArchivoZIPPDF_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}