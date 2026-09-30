package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Si el usuario hace uso de la opción multiselect el filtro debe poblarse con \"Múltiples Seleccionados\"  ( Aplica para todos los filtros excepto rango de fechas)"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_253_Validar opción multiselect en Cambios Físicos  con usuario Admin
*
*PREREQUISITOS:
*1. Credenciales para ingresar al Portal cometa, validar el formato con la liga ux
*2. Liga de Portal Cometa
*
*INSUMOS: 
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Alejandro Valdez
* @date Septiembre 2025
*/
public class ATC_FT_253_MultiselectCambiosFisicosAdmin extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_253_MultiselectCambiosFisicosAdmin_test (HashMap<String, String> data) throws Exception {
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
		 * Paso 4 - Ingresar a "Cambios Físicos"
		 */
		addStep("Ingresar a \"Cambios Físicos\"");
		pc.opcDirectosPortalCometa(false);
		validacion = pc.selectCambiosFisicosSE();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.screenCambiosFisicos();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta"); 
		
		/*
		 * Paso 5 - Seleccionar las diferentes opciones para filtrar de un campo
		 */
		addStep("Seleccionar las diferentes opciones para filtrar de un campo");
		validacion = pc.selectListaFuenteAbastoyValidarMultipleSelección();
		assertTrue(validacion,"No se permite ingresar el valor");
		

	
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
		return "1. Credenciales para ingresar al Portal cometa, validar el formato con la liga ux\n"
				+ "2. Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Si el usuario hace uso de la opción multiselect el filtro debe poblarse con \"Múltiples Seleccionados\"  ( Aplica para todos los filtros excepto rango de fechas)\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_253_MultiselectCambiosFisicosAdmin_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}