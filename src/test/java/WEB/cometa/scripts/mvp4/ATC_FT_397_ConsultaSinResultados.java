package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que el usuario filtre algun dato y no haga match con la información aparecera un mensaje \"No se encuentra información con los filtros seleccionados\""
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* ATC_FT_397_ConsultaSinResultados
*
*PREREQUISITOS:
*1. Credenciales para el perfil   Admin, OXXO OS, OXXO Plaza, Administrativo Operativo y Comercial
*2. Validar portal comenta
*3. Validar pantalla "Cambios Fisicos"
*
*INSUMOS: 
 *Contar con información disponible en tabla xxcm_historico_correos.
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Maximino Ramirez
* @date Enero 2026
*/
public class ATC_FT_397_ConsultaSinResultados extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_397_ConsultaSinResultados_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
		boolean validacion = pc.obtenerRutaPropuesta(user);
		assertTrue(validacion,"No se obtuvieron datos en la tabla");
		String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		
		/*
		 * Paso 1 - Ingresar a la liga del portal
		 */
		addStep("Ingresar a la liga del portal");
		u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
		validacion = pc.loginScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		/*
		 * paso 2 - Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion
		 */
		addStep("Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion");
		validacion = pc.iniciarSesion(user, password);
		assertTrue(validacion,"No se puede iniciar sesión de manera correcta");
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
		addStep("Se ingresa a DIRECTOS - Cambios Fisicos");
		validacion = pc.opcDirectosPortalCometa(true);
		assertTrue(validacion,"No se ingresa de manera correcta");
		/*
		 * Paso 5 - Ingresar a "Cambios Físicos" 
		 */
		addStep("Ingresar a la pantalla Cambios Físicos");
		validacion = pc.selectCambiosFisicosSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		/*
		 * Paso 6 - Seleccionar el botón "Cambios físicos pendientes actuales"
		 */
		addStep("Seleccionar el botón \"Cambios físicos pendientes actuales\"");
		validacion = pc.btnCambiosFisicosPendientesActuales(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		/*
		 * Paso 7 - Selecciona combinación de filtros que no contengan información
		 */
		addStep("Selecciona los filtros a buscar e ingresa un SKU aleatorio sin coincidencias");
		validacion = pc.selectFa(false);
		assertTrue(validacion, "No se seleccionó la fuente de abasto");
        
		validacion = pc.selectTerceraZona();
		assertTrue(validacion, "No se pudo seleccionar la zona seleccionada");
		addStep("Seleccionar botón \"Consultar\" y se valida mensaje");
		// Esta función genera el SKU, da ENTER, espera el loader y valida el mensaje
		validacion = pc.buscarSkuSinCoincidencias(true); 
		assertTrue(validacion, "No se mostró el mensaje esperado tras la búsqueda ");



	}
	
	@AfterMethod
	public void afterMethod() {
		if (u != null && u.getDriver() != null) {
			u.getDriver().quit();
		}
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
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
		return "Validar que el usuario filtre algun dato y no haga match con la información aparecera un mensaje \"No se encuentra información con los filtros seleccionados\" ";
	}

	@Override
	public String setTestDesigner() {
		return"Maximino Ramirez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_397_ConsultaSinResultados_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}