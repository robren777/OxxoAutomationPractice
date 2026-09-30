package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que cuando el usuario pase el cursor sobre el ícono de paginación  debe de aparecer un tool tip de acuerdo a la descripción del icono  “|< "" debe de aparecer un tool tip que diga: ""Primera página""
* Cuando el usuario pase el cursor sobre el ícono de paginación representado por “< "" debe de aparecer un tool tip que diga: ""Página anterior""
* Cuando el usuario pase el cursor sobre el ícono de paginación representado por “> "" debe de aparecer un tool tip que diga: ""Página siguiente""
* Cuando el usuario pase el cursor sobre el ícono de paginación representado por “>|"" debe de aparecer un tool tip que diga: ""Última página"" ""Cambios Físicos"" Directos"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_337_ Validar que cuando el usuario pase el cursor sobre el ícono de paginación  debe de aparecer un tool tip de acuerdo a la descripción del icono, "Cambios Físicos" Directos
*
*PREREQUISITOS:
*1. Credenciales para el perfil DIRECTOS con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial], validar desde el criterio
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
public class ATC_FT_337_TooltipPaginacionCambiosFisicos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_337_TooltipPaginacionCambiosFisicos_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
		boolean validacion = pc.obtenerRegistroTipo2(true);
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
		 * Paso 4 - Ingresar a DIRECTOS
		 */
		addStep("Ingresar a DIRECTOS");
		validacion = pc.opcDirectosPortalCometa(false);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.directosPortalCometaSc();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 5 - Ingresar a "Cambios Físicos" Directos
		 */
		addStep("Ingresar a \"Cambios Físicos\" Directos");
		validacion = pc.selectCambiosFisicosSE();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.screenCambiosFisicos();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 6 - Realizar el filtro en campo Plaza 
		 */
		addStep("Realizar el filtro en campo Plaza");
		validacion = pc.opcListaPlaza();
		assertTrue(validacion,"No se muestra la lista de manera correcta");
		
		/*
		 * Paso 7 - Seleccionar una plaza
		 */
		addStep("Seleccionar una plaza");
		validacion = pc.selectListaPlazayValidarMultipleSelección();
		assertTrue(validacion,"No se permite seleccionar correctamente");
		
		/*
		 * Paso 8 - Realizar el filtro de "Rango de Fecha"
		 */
		addStep("Realizar el filtro de \"Rango de Fecha\"");
		validacion = pc.sltFechase(6);
		assertTrue(validacion,"No se pudo seleccionar correctamente");
		
		/*
		 * Paso 9 - Dar clic en el Boton Consultar
		 */
		addStep("Dar clic en el Boton Consultar");
		validacion = pc.opcConsultar(true);
		assertTrue(validacion,"No se permite seleccionar correctamente");
		
		/*
		 * Paso 10 - Validar que se muestre la informacion de más reciente a más antigua
		 */
		addStep("Validar que se muestre la informacion de más reciente a más antigua");
		pc.scrollMid();
		validacion = pc.registroFechaDevPrimUlt();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 11 - Validar que se visualice 20 registros por página al inicio
		 */
		addStep("Validar que se visualice 20 registros por página al inicio");
		validacion = pc.contadorRegistros();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 12 - 12. Validar al pasar el cursor sobre el ícono de paginación “|< " muestre un tool tip "Primera página"
		 */
		addStep("12. Validar al pasar el cursor sobre el ícono de paginación “|< \" muestre un tool tip \"Primera página\"");
		pc.clicPagSiguiente(false);
		validacion = pc.iconoPrimeraPag();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 13 - Validar al pasar el cursor sobre el ícono de paginación “< " muestre un tool tip "Página anterior" 
		 */
		addStep("Validar al pasar el cursor sobre el ícono de paginación “< \" muestre un tool tip \"Página anterior\" ");
		validacion = pc.iconoPagAnterior();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 14 - Validar al pasar el cursor sobre el ícono de paginación “> " muestre un tool tip "Página Siguiente" 
		 */
		addStep("Validar al pasar el cursor sobre el ícono de paginación “> \" muestre un tool tip \"Página Siguiente\" ");
		validacion = pc.iconoPagSiguiente();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 15 - Validar al pasar el cursor sobre el ícono de paginación “>| " muestre un tool tip "Última página" 
		 */
		addStep("Validar al pasar el cursor sobre el ícono de paginación “>| \" muestre un tool tip \"Última página\" ");
		validacion = pc.iconoUltimaPag();
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
		return "1. Credenciales para el perfil DIRECTOS con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial], validar desde el criterio\n"
				+ "2. Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que cuando el usuario pase el cursor sobre el ícono de paginación  debe de aparecer un tool tip de acuerdo a la descripción del icono  “|< \"\" debe de aparecer un tool tip que diga: \"\"Primera página\"\"\r\n"
				+ "Cuando el usuario pase el cursor sobre el ícono de paginación representado por “< \"\" debe de aparecer un tool tip que diga: \"\"Página anterior\"\"\r\n"
				+ "Cuando el usuario pase el cursor sobre el ícono de paginación representado por “> \"\" debe de aparecer un tool tip que diga: \"\"Página siguiente\"\"\r\n"
				+ "Cuando el usuario pase el cursor sobre el ícono de paginación representado por “>|\"\" debe de aparecer un tool tip que diga: \"\"Última página\"\" \"\"Cambios Físicos\"\" Directos\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_337_TooltipPaginacionCambiosFisicos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}