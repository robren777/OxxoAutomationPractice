package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que la herramienta de autocomplete funcione correctamente"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_308_Validar que la herramienta de autocomplete de filtro Plaza con usuarios Externos
*
*PREREQUISITOS:
*1. Credenciales para el perfil   Admin, OXXO OS, OXXO Plaza, Administrativo Operativo y Comercial
*2. Validar portal comenta
*3. Validar pantalla "Cambios Fisicos"
*
*INSUMOS: 
 *Contar con información disponible en tabla xxcm_historico_directos_cf_view.
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Alejandro Valdez
* @date Septiembre 2025
*/
public class ATC_FT_308_ValidarAutocompletePlaza extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_308_ValidarAutocompletePlaza_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
		boolean validacion = pc.ValidaBDExistanDatosFiltrosGridCambiosFisicos(true, user);
        validacion = pc.obtenerPlazaCambiosFisicos(false, user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla");
        String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		 
        /*
		 * Paso 1 - Ingresar a la liga del portal con las con credenciales 
		 */
		addStep("Ingresar a la liga del portal con las con credenciales"); 
		u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
		validacion = pc.loginScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
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
		 * Paso 5 - Seleccionar campo Plaza
		 */
		addStep("Seleccionar campo Plaza");
		validacion = pc.opcListaPlaza();
		assertTrue(validacion,"No se permite seleccionar el campo");
		
		/*
		 * Paso 6 - Ingresar caracteres que coincidan con alguna Plaza 
 */
		addStep("Ingresar caracteres que coincidan con alguna Plaza");
		validacion = pc.sugerenciasPlaza();
		assertTrue(validacion,"No se muestra correctamente");

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
		return "1. Credenciales para el perfil   Admin, OXXO OS, OXXO Plaza, Administrativo Operativo y Comercial\n"
				+ "2. Validar portal comenta\n"
				+ "3. Validar pantalla \"Cambios Fisicos\"";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que la herramienta de autocomplete funcione correctamente";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_308_ValidarAutocompletePlaza_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}