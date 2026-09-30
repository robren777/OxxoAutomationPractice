package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que se respete las caracteriticas por cada perfil de usuario Admin del modulo \"Cambios Fisicos\" DIRECTOS"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_240_Validar que se respete las caracteriticas por cada perfil de usuario Admin "Cambios Fisicos" DIRECTOS
*
*PREREQUISITOS:
*1. Credenciales para el perfil DIRECTOS con usuario Admin, validar desde el criterio
*2. Liga de Portal Cometa
*
*INSUMOS: 
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Alejandro Valdez
* @date Septiembre 2025
*/
public class ATC_FT_240_PerfilAdminCambiosFisicosDirectos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_240_PerfilAdminCambiosFisicosDirectos_test (HashMap<String, String> data) throws Exception {
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
		u.hardWait(12);
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
		 * Paso 4 - Ingresar a DIRECTOS
		 */
		addStep("Ingresar a DIRECTOS");
		validacion = pc.opcDirectosPortalCometa(false);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.directosPortalCometaSc();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 5 - Ingresar a "Cambios Físicos" 
 */
		addStep("Ingresar a \"Cambios Físicos\"");
		validacion = pc.selectCambiosFisicosSE();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.screenCambiosFisicos();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta"); 
		
		/*
		 * Paso 6 - Validar en el menú en la pantalla parte superior izquierda, el perfil del usuario Admin
		 */
		addStep("Validar en el menú en la pantalla parte superior izquierda, el perfil del usuario Admin");
		validacion = pc.validUsuario();
		assertTrue(validacion,"No se muestra de manera correcta");
		
		/*
		 * Paso 7 - Validar que el perfil de usuario Admin, visualice los modulos Inicio , CEDIS, Directos, FAQS Directos, FAQS CEDIS,
		 */
		addStep("Validar que el perfil de usuario Admin, visualice los modulos Inicio , CEDIS, Directos, FAQS Directos, FAQS CEDIS,");
		pc.btnBack();
		pc.btnBack();
		validacion = pc.portalCometaScAdmin();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		

	
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
		return "1. Credenciales para el perfil DIRECTOS con usuario Admin, validar desde el criterio\n"
				+ "2. Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se respete las caracteriticas por cada perfil de usuario Admin del modulo \"Cambios Fisicos\" DIRECTOS\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_240_PerfilAdminCambiosFisicosDirectos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}