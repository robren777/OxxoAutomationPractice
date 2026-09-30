package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que se pueda cambiar la vista de registros por página y funcione correctamente 5-10-15-20"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_413_Validar cambiar registros por pagina en paginado en centro de descargas
*
*PREREQUISITOS:
*1. Credenciales  [Admin, Administrativo Operativo, Comercial] validar desde el criterio  
*2. Liga de Portal Cometa 
*3. Contar con más de 20 registros de descarga generado (Se generan en modulo comprobantes de compra)
*
*INSUMOS: 
 *Contar con información disponible en tabla public.xxcm_historico_directos_view.
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Alejandro Valdez
* @date Noviembre 2025
*/
public class ATC_FT_413_CambiarRegistrosPaginaDescargas extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_413_CambiarRegistrosPaginaDescargas_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
		boolean validacion = pc.validarYObtenerFecha(user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla xxcm_historico_cedis_view");
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
		 * Paso 4 - Se da clic en menú centro de descarga
		 */
		addStep("Se da clic en menú centro de descarga");
		validacion = pc.opcCentroDescargas(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.scCentroDeDescargas();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 5 - Seleccionar rango de fechas
		 */
		addStep("Seleccionar rango de fechas");
		u.hardWait(2);
		validacion = pc.FiltroRangoFechasCentroDescarga(6,true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		pc.opcConsultar(false);
		
		/*
		 * Paso 6 - Desplazar al final de los registros - Visualizar filtro "Registros por pagina"
		 */
		addStep("Desplazar al final de los registros - Visualizar filtro \"Registros por pagina\"");
		validacion = pc.valFiltroRegPorPagina();
		assertTrue(validacion,"No se pudo validar que se muestre correctamente");
		
		/*
		 * Paso 7 - Abrir lista desplegable del filtro "Registros por pagina" con clic de raton
		 */
		addStep("Abrir lista desplegable del filtro \"Registros por pagina\" con clic de raton");
		validacion = pc.selFiltroPorPagina();
		assertTrue(validacion,"No se pudo validar que se muestre correctamente");
		
		/*
		 * Paso 8 - Seleccionar una opcion de lista desplegable del filtro "Registros por pagina"
		 */
		addStep("Seleccionar una opcion de lista desplegable del filtro \"Registros por pagina\"");
		validacion = pc.selCantidadDeRegistrosPorPagina(15);
		assertTrue(validacion,"No se pudo validar que se muestre correctamente");
		
		/*
		 * Paso 9 - Validar cantidad de registros mostrados sea la misma a la seleccionada en el filtro "Registros por pagina"
		 */
		addStep("Validar cantidad de registros mostrados sea la misma a la seleccionada en el filtro \"Registros por pagina\"");
		validacion = pc.valCantidadRegistrosMostrados(15, true);
		assertTrue(validacion,"No se pudo validar que se muestre correctamente");
		

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
		return "1. Credenciales  [Admin, Administrativo Operativo, Comercial] validar desde el criterio  \n"
				+ "2. Liga de Portal Cometa \n"
				+ "3. Contar con más de 20 registros de descarga generado (Se generan en modulo comprobantes de compra)";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se pueda cambiar la vista de registros por página y funcione correctamente 5-10-15-20";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_413_CambiarRegistrosPaginaDescargas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}