package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar si no hay coincidencias, deberá mostrarse el mensaje: \"No se encontraron resultados para tu búsqueda.\""
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* ATC_FT_400_BusquedaSkuDescripcionSinMatch
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
public class ATC_FT_400_BusquedaSkuDescripcionSinMatch extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_400_BusquedaSkuDescripcionSinMatch_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase);
		
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
		 * Paso 4 - Seleccionar el botón Cambios físicos pendientes actuales
		 */
		addStep("Seleccionar el botón Cambios físicos pendientes actuales");
		validacion = pc.opcDirectosPortalCometa(true);
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		/*
		 * Paso 5 - Ingresar a la pantalla Cambios Físicos
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
		 * Paso 7 - Validar que exista un buscador llamado Buscar SKU o descripción
		 */
		addStep("Validar que exista un campo de texto llamado Buscar SKU o descripción");
		validacion = pc.validarPlaceholderBusqueda(false);
		assertTrue(validacion,"Se encuentra el botón "+"Buscar SKU o descripcion");
		/*
		 * Paso 8 - Seleccionar botón Consultar
		 */
		addStep("Seleccionar botón Consultar");
		validacion = pc.opcConsultarHabilitada();
		assertTrue(validacion,"El botón se encuentra deshabilitado");
		validacion = pc.opcConsultar(true);
		assertTrue(validacion,"El botón se encuentra deshabilitado");
		/*
		 * Paso 9 - Seleccionar buscador llamado Buscar SKU o descripción
		 * Paso  - Ingresar el SKU sin coincidencias + Enter
		 */
		addStep("Seleccionar buscador llamado Buscar SKU o descripción");
		validacion = pc.buscarSkuSinCoincidencias(true);
		assertTrue(validacion,"No se encontraron resultados para tu búsqueda. (SKU)");
		/*
		 * Paso 10 - Ingresar un Descripcion sin coincidencias + Enter
		 */
		addStep("Ingresar un Descripcion sin coincidencias + Enter");
		validacion = pc.buscarDescriptionSinCoincidencias(true);
		assertTrue(validacion,"No se encontraron resultados para tu búsqueda. (Descripción)");
		/*
		 * Paso 11 - Borrar el texto ingresado
		 */
		addStep("Borrar el texto ingresado");
		validacion = pc.limpiarYValidarFiltro(true);
	    assertTrue(validacion, "El texto se elimino correctamente");

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
		return "Validar si no hay coincidencias, deberá mostrarse el mensaje:\n"
				+ "\"No se encontraron resultados para tu búsqueda.\"";
	}

	@Override
	public String setTestDesigner() {
		return "Maximino Ramirez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_400_BusquedaSkuDescripcionSinMatch_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}