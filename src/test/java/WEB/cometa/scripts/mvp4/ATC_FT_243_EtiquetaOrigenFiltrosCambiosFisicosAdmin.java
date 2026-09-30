package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar  que los filtros tengan etiquetas claras y estén correctamente configurados con su origen de datos, para asegurar que la información mostrada sea precisa y confiable."
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_243_Validar Etiqueta y Origen de Datos para Filtros en Cambios Físicos con usuario Admin
*
*PREREQUISITOS:
*1. Credenciales para ingresar al Portal cometa, validar el formato con la liga ux
*2. Liga de Portal Cometa
*3. Contar con acceso a BD Postgres: oxzaspocmdbq01.postgres.database.azure.com
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
public class ATC_FT_243_EtiquetaOrigenFiltrosCambiosFisicosAdmin extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_243_EtiquetaOrigenFiltrosCambiosFisicosAdmin_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
		boolean validacion = pc.ValidaBDExistanDatosFiltrosGridCambiosFisicos(true, user);
        validacion = pc.obtenerProveedorCambiosFisicos(true, user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla");
        validacion = pc.obtenerFuenteAbastoCambiosFisicos(false, user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla");
        validacion = pc.obtenerZonaCambiosFisicos(false, user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla");
        validacion = pc.obtenerRegionCambiosFisicos(false, user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla");
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
		 * Paso 5 - Validar el filtro "Proveedor "
		 */
		addStep("Validar el filtro \"Proveedor \"");
		pc.opcListaProveedorSe();
		validacion = pc.sugerenciasProveedor();
		assertTrue(validacion,"No se permite seleccionar el campo");
		
		/*
		 * Paso 6 - Validar filtro "Fuente de Abasto"
		 */
		addStep("Validar filtro \"Fuente de Abasto\"");
		pc.opcListaFuenteAbastoSe();
		validacion = pc.sugerenciasFA();
		assertTrue(validacion,"No se permite seleccionar el campo");
		
		/*
		 * Paso 7 - Validar filtro "Zona"
		 */
		addStep("Validar filtro \"Zona\"");
		pc.opcListaZona(false);
		validacion = pc.sugerenciasZona();
		assertTrue(validacion,"No se permite seleccionar el campo");
		
		/*
		 * Paso 8 - Validar filtro "Región"
		 */
		addStep("Validar filtro \"Región\"");
		pc.opcListaRegionSe();
		validacion = pc.sugerenciasRegion();
		assertTrue(validacion,"No se permite seleccionar el campo");
		
		/*
		 * Paso 9 - Validar filtro "Plaza"
		 */
		addStep("Validar filtro \"Plaza\"");
		pc.opcListaPlazaSe();
		validacion = pc.sugerenciasPlaza();
		assertTrue(validacion,"No se permite seleccionar el campo");
		
		/*
		 * Paso 10 - Validar filtro "Rango de Fecha"
		 */
		addStep("Validar filtro \"Rango de Fecha\"");
		validacion = pc.sltFechase(2);
		assertTrue(validacion,"No se permite seleccionar");
		

	
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
				+ "2. Liga de Portal Cometa\n"
				+ "3. Contar con acceso a BD Postgres: oxzaspocmdbq01.postgres.database.azure.com";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar  que los filtros tengan etiquetas claras y estén correctamente configurados con su origen de datos, para asegurar que la información mostrada sea precisa y confiable.\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_243_EtiquetaOrigenFiltrosCambiosFisicosAdmin_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}