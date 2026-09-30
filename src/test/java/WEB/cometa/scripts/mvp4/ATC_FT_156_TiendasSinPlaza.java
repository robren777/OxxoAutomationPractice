package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que al no tener seleccionado una Plaza en el filtro, y marcar uno o varias Tiendas, el sistema nos muestre los registros ligados a las Tiendas seleccionadas. \"Devoluciones\" DIRECTOS, aplica para todos los usuarios"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_156_Validar que al no tener seleccionado Plaza y marcar uno o varias Tiendas, devuelva registros ligados a Tiendas seleccionadas
*
*PREREQUISITOS:
*1. Credenciales para el perfil DIRECTOS,con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial] validar desde el criterio y el formato con la liga ux    
*2.Liga de Portal Cometa
*
*INSUMOS: 
 *Contar con información disponible en tabla xxcm_historico_directos_devoluciones_view.
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
* @author Alejandro Valdez
* @date Julio 2025
*/
public class ATC_FT_156_TiendasSinPlaza extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_156_TiendasSinPlaza_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
        boolean validacion = pc.ValidaBDExistanDatosFiltrosGridDirectosDev(true, user);
        validacion = pc.obtenerTiendaDirectosDev(true, user);
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
		 * Paso 5 - Ingresar a la pantalla "Devoluciones"
		 */
		addStep("Ingresar a la pantalla \"Devoluciones\"");
		validacion = pc.selectDevolucionesSE();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.screenDevoluciones();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta"); 
		
		/*
		 * Paso 6 - Validar se muestre registros al entrar a la pantalla sin filtros seleccionados
		 */
		addStep("Validar se muestre registros al entrar a la pantalla sin filtros seleccionados");
		validacion = pc.validarFiltrosClean();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		pc.scrollUp(); 
	
		/*
		 * Paso 7 - Validar que no este seleccionado una Plaza en el filtro
		 */
		addStep("Validar que no este seleccionado una Plaza en el filtro");
		pc.scrollUp();
		validacion = pc.validarPlazaClean();
		assertTrue(validacion,"No se muestra sin información los filtros");
		
		/*
		 * Paso 8 - Seleccionar  uno o varias Tiendas
		 */
		addStep("Seleccionar  uno o varias Tiendas");
		validacion = pc.selectListaTienda();
		assertTrue(validacion,"No se permite ingresar el valor");

		/*
		 * Paso 9 - Validar que el sistema muestre los registros ligados a las Tiendas seleccionadas
		 */
		addStep("Validar que el sistema muestre los registros ligados a las Tiendas seleccionadas");
		pc.sltFechas(2);
		pc.opcConsultar(false);
		pc.scrollDown();
		validacion = pc.registroTiendaDev();
		assertTrue(validacion,"No se muestra ningun registro de información"); 
		

	
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
		return "1. Credenciales para el perfil DIRECTOS,con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial] validar desde el criterio y el formato con la liga ux    \n"
				+ "2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al no tener seleccionado una Plaza en el filtro, y marcar uno o varias Tiendas, el sistema nos muestre los registros ligados a las Tiendas seleccionadas. \"Devoluciones\" DIRECTOS, aplica para todos los usuarios\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_156_TiendasSinPlaza_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}