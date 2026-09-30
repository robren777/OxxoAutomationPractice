package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que cuando el usuario escriba dentro del campo, se vayan mostrando las coincidencias autocompletadas y permita seleccionar uno o varias Zonas, \"Devoluciones\" DIRECTOS, aplica para todos los usuarios"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_137_Validar que al escribir sobre campo "Zona" se muestre las opciones de autocomplete y permita seleccionar multiples Zonas
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
public class ATC_FT_137_EscrituraMultipleZona extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_137_EscrituraMultipleZona_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		String alfanumerico = data.get("valorAlfa");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
        boolean validacion = pc.ValidaBDExistanDatosFiltrosGridDirectosDev(true, user);
        validacion = pc.obtenerZonaDirectosDev(true, user);
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
		 * Paso 7 - Al posicionarse sobre el campo Zona se activa el campo y muestra la lista de selección del filtro 
		 */
		addStep("Al posicionarse sobre el campo Zona se activa el campo y muestra la lista de selección del filtro");
		validacion = pc.opcListaZona(true);
		assertTrue(validacion,"No se muestra la lista de manera correcta");
		
		/*
		 * Paso 8 - Validar que permita escribir dentro del campo Zona 
		 */
		addStep("Validar que permita escribir dentro del campo Zona");
		validacion = pc.textAlfanumericoZonaDevo(alfanumerico);
		assertTrue(validacion,"No se permite ingresar el valor");
		
		/*
		 * Paso 9 - Validar que se muestre las coincidencias autocompletadas   
		 */
		addStep("Validar que se muestre las coincidencias autocompletadas");
		validacion = pc.sugerenciasZona();
		pc.opcLimpiarFiltrosCC();
		assertTrue(validacion,"No se muestran las coincidencias de manera correcta");
		
		/*
		 * Paso 10 - Validar que permita seleccionar uno o varias Zonas
		 */
		addStep("Validar que permita seleccionar uno o varias Zonas");
		validacion = pc.selectListaZonayValidarSeleccion(true);
		assertTrue(validacion,"No se permite ingresar el valor");
		

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
		return "1. Credenciales para el perfil DIRECTOS,con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial] validar desde el criterio y el formato con la liga ux    "
				+ "2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que cuando el usuario escriba dentro del campo, se vayan mostrando las coincidencias autocompletadas y permita seleccionar uno o varias Zonas, \"Devoluciones\" DIRECTOS, aplica para todos los usuarios\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_137_EscrituraMultipleZona_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}