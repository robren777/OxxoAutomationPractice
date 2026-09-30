package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que en la parte inferior de la pantalla esten visibles los íconos de navegación, \"Cambios Físicos\" DIRECTOS"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_353_Validar íconos de navegación en el modulo "Cambios Físicos" DIRECTOS
*
*PREREQUISITOS:
*1. Credenciales para el perfil   Admin, OXXO OS, OXXO Plaza, Administrativo Operativo y Comercial
*2. Acceso a Portal Comenta
*3. Acceso a la pantalla "Cambios Fisicos"
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
public class ATC_FT_353_IconosNavegacionCambiosFisicos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_353_IconosNavegacionCambiosFisicos_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
		boolean validacion = pc.obtenerRegistroCorreoCFTipo1(true,user);
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
		u.hardWait(12);
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
		 * Paso 5 - Ingresar a "Cambios Físicos" 
		 */
		addStep("Ingresar a \"Cambios Físicos\"");
		validacion = pc.selectCambiosFisicosSE();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.screenCambiosFisicos();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 6- Realizar algún filtro + Rango de Fechas
		 */
		addStep("Realizar algún filtro + Rango de Fechas");
		validacion = pc.sltFechase(6);
		assertTrue(validacion,"No se permite seleccionar correctamente");
		
		/*
		 * Paso 7 - Seleccionar el botón Consultar
		 */
		addStep("Dar clic en el botón Consultar");
		validacion = pc.opcConsultar(true);
		assertTrue(validacion,"No se permite seleccionar correctamente");
		
		/*
		 * Paso 8 - Posicionarse sobre el ícono ">"
		 */
		pc.scrollBott();
		addStep("Posicionarse sobre el ícono \">\"");
		validacion = pc.iconoPagSiguiente();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 9 - Clic en el ícono ">"
		 */
		addStep("Clic en el ícono \">\"");
		validacion = pc.clicPagSiguiente(true);
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 10 - Posicionarse sobre el ícono "<"
		 */
		addStep("Posicionarse sobre el ícono \"<\"");
		validacion = pc.iconoPagAnterior();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 11 - Clic en el ícono "<"
		 */
		addStep("Clic en el ícono \"<\"");
		validacion = pc.clicPagAnterior();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 12 - Posicionarse sobre el ícono ">|"
		 */
		addStep("Posicionarse sobre el ícono \">|\"");
		validacion = pc.iconoUltimaPag();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 13 - Clic en el ícono ">|"
		 */
		addStep("Clic en el ícono \">|\"");
		validacion = pc.clicUltimaPagina();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 14 - Posicionarse sobre el ícono "|<"
		 */
		addStep("Posicionarse sobre el ícono \"|<\"");
		validacion = pc.iconoPrimeraPag();
		assertTrue(validacion,"No se muestra correctamente");
		
		/*
		 * Paso 15 - Clic en el ícono "|<"
		 */
		addStep("Clic en el ícono \"|<\"");
		validacion = pc.clicPrimeraPagina();
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
				+ "2. Acceso a Portal Comenta\n"
				+ "3. Acceso a la pantalla \"Cambios Fisicos\"";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que en la parte inferior de la pantalla esten visibles los íconos de navegación, \"Cambios Físicos\" DIRECTOS";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_353_IconosNavegacionCambiosFisicos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}