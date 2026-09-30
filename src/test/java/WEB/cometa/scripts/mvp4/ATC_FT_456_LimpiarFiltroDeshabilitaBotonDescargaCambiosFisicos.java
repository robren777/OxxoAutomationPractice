package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que al dar click en consultar y despues click en botón limpiar filtro, el botón descarga se deshabilite"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_456_Validar que al dar Click al botón limpiar filtro despues de boton consultar, el botón descarga se deshabilite en pantalla de Cambios Físicos
*
*PREREQUISITOS:
*1. Credenciales [Admin, Administrativo Operativo, Comercial] validar desde el criterio 
*2. Liga de Portal Cometa 
*
*INSUMOS: 
 *Contar con información disponible en tabla public.xxcm_historico_directos_cf_view.
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Alejandro Valdez
* @date Diciembre 2025
*/
public class ATC_FT_456_LimpiarFiltroDeshabilitaBotonDescargaCambiosFisicos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_456_LimpiarFiltroDeshabilitaBotonDescargaCambiosFisicos_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		/*
		 * Paso 1 - Ingresar a la liga del portal
		 */
		addStep("Ingresar a la liga del portal"); 
		u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
		boolean validacion = pc.loginScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		
		/*
		 * Paso 2 - Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion
		 */
		addStep("Ingresar credenciales correspondientes al perfil DIRECTOS usuario para inicio de sesion");
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
		 * Paso 4 - Ingresar a Directos
		 */
		addStep("Ingresar a Directos");
		validacion = pc.opcDirectosPortalCometa(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta"); 
		
		/*
		 * Paso 5 - Ingresar a "Cambios Físicos" 
		 */
		addStep("Ingresar a \"Cambios Físicos\"");
		validacion = pc.selectCambiosFisicosSE();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.MensajeEspereDesaparece(false);
		assertTrue(validacion,"No se pudo validar de manera correcta");
		validacion = pc.screenCambiosFisicos();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta"); 
		
		/*
		 * Paso 6 - Seleccionar rango de fechas
		 */
		addStep("Seleccionar rango de fechas");
		validacion = pc.sltFechasCF(1);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		
		/*
		 * Paso 7 - Dar clic en el Botón Consultar
		 */
		addStep("Dar clic en el Botón Consultar");
		validacion = pc.opcConsultar(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		
		/*
		 * Paso 8 - Validar que el botón descarga se muestre habilitado - Se muestra 
		 * botón descarga de resumen de todos los comprobantes filtrados
		 */
		addStep("Validar que el botón descarga se muestre habilitado - Se muestra botón descarga de resumen de todos los comprobantes filtrados");
		validacion = pc.MensajeEspereDesaparece(false);
		assertTrue(validacion,"No se pudo validar de manera correcta");
		validacion = pc.valDescargaCompFiltradosHabilitada(true);
		assertTrue(validacion,"El botón se encuentra deshabilitado");
		
		/*
		 * Paso 9 - Dar click en botón Limpiar Filtros
		 */
		addStep("Dar click en botón Limpiar Filtros");
		pc.scrollTop();
		validacion = pc.opcLimpiarFiltrosCC();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		
		/*
		 * Paso 10 - Validar que el botón descarga deshabilitado - No se muestra 
		 * botón descarga de resumen de todos los comprobantes filtrados
		 */
		addStep("Validar que el botón descarga deshabilitado - No se muestra botón descarga de resumen de todos los comprobantes filtrados");
		validacion = pc.MensajeEspereDesaparece(false);
		assertTrue(validacion,"No se pudo validar de manera correcta");
		validacion = pc.valDescargaCompFiltradosDeshabilitada(true);
		assertTrue(validacion,"El botón se encuentra habilitado");
		


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
		return "1. Credenciales [Admin, Administrativo Operativo, Comercial] validar desde el criterio \n"
				+ "2. Liga de Portal Cometa ";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al dar click en consultar y despues click en botón limpiar filtro, el botón descarga se deshabilite";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_456_LimpiarFiltroDeshabilitaBotonDescargaCambiosFisicos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}