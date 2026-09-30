package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que los filtros se poblen con la información disponible en recibo agil ( modulo de cambios fisicos), Para evitar seleccionar filtros vacíos y garantizar que toda búsqueda devuelva resultados útiles."
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_267_Validar informacion en filtros en modulo Cambio Físicos con usuario Admin
*
*PREREQUISITOS:
*1. Credenciales para ingresar al Portal cometa, validar el formato con la liga ux
*2. Liga de Portal Cometa
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
public class ATC_FT_267_ValidarFiltrosCambioFisicosAdmin extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_267_ValidarFiltrosCambioFisicosAdmin_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
		boolean validacion = pc.ValidaBDExistanDatosFiltrosGridCambiosFisicos(false, user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla");
        validacion = pc.obtenerZonaCambiosFisicos(false, user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla");
        validacion = pc.obtenerRegionCambiosFisicos(false, user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla");
        validacion = pc.obtenerPlazaCambiosFisicos(true, user);
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
		 * Paso 5 - Validar los filtros
		 * Validar que se actualice la información del filtro
		 */
		addStep("Validar los filtros - Validar que se actualice la información del filtro");
		validacion = pc.selectRegion();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.selectCrPlaza(); 
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.selectCrTienda();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.sltFechase(5);
		assertTrue(validacion,"No se pudieron seleccionar las fechas de manera correcta.");
		pc.opcLimpiarFiltrosCC();
		
		/*
		 * Paso 6 - Realizar una búsqueda filtrando menos de 3 años de antiguedad
		 */
		addStep("Realizar una búsqueda filtrando menos de 3 años de antiguedad");
		pc.sltFechas(5);
		validacion = pc.opcConsultar(false);
		validacion = pc.regCambiosFisicos();
		assertTrue(validacion,"No se ve información");
	
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
		return "Validar que los filtros se poblen con la información disponible en recibo agil ( modulo de cambios fisicos), Para evitar seleccionar filtros vacíos y garantizar que toda búsqueda devuelva resultados útiles.\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_267_ValidarFiltrosCambioFisicosAdmin_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}