package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que al utilizar el usuario perfil comercial se visualicen los sub módulos \"Comprobantes de Compra\", \"Cambios Físicos\" y \"Devoluciones\" y se deben de ocultar los sub módulos \"Reportes\" y \"Gráficas\""
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_044_Validar que el usuario Perfil comercial tenga visibilidad de los sub modulos especificos en portal cometa directos.
*
*PREREQUISITOS:
*1. Credenciales para el perfil comercial  , validar el formato con la liga ux
*2.Liga de Portal Cometa
*
*INSUMOS: 
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
*
* @author Alejandro Valdez
* @date Febrero 2026
*/
public class ATC_FT_044_ValidarVisibilidadSubmodulosPerfilComercialEnPortalCometa extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_044_ValidarVisibilidadSubmodulosPerfilComercialEnPortalCometa_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		/*
		 * Paso 1 - Ingresar a la liga del portal con usuario perfil Comercial
		 */
		addStep("Ingresar a la liga del portal con usuario perfil Comercial"); 
		u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
		boolean validacion = pc.loginScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		
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
		 * Paso 4 - Validar que se visualice sub modulo comprobante de compra
		 */
		addStep("Validar que se visualice sub modulo comprobante de compra");
		pc.opcDirectosPortalCometa(false);
		validacion = pc.opcComprobantesCompraOS();
		assertTrue(validacion,"No se muestra de manera correcta");
		
		/*
		 * Paso 5 - Validar que se visualice sub modulo cambios fisicos
		 */
		addStep("Validar que se visualice sub modulo cambios fisicos");
		validacion = pc.opcCambiosFisicosOS();
		assertTrue(validacion,"No se muestra de manera correcta");
		
		/*
		 * Paso 6 - Validar que se visualice sub modulo devoluciones
		 */
		addStep("Validar que se visualice sub modulo devoluciones");
		validacion = pc.opcDevolucionesOS();
		assertTrue(validacion,"No se muestra de manera correcta");
		
		/*
		 * Paso 7 - Validar que se oculte sub modulo reportes
		 */
		addStep("Validar que se oculte sub modulo reportes");
		validacion = pc.opcOcultaReportes();
		assertTrue(validacion,"No se encuentra Oculto de manera correcta");
		
		/*
		 * Paso 8 - Validar que se oculte sub modulo gráficas
		 */
		addStep("Validar que se oculte sub modulo gráficas");
		validacion = pc.opcOcultaGraficas();
		assertTrue(validacion,"No se encuentra Oculto de manera correcta"); 
		

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
		return "1. Credenciales para el perfil comercial  , validar el formato con la liga ux"
				+ "2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que al utilizar el usuario perfil comercial se visualicen los sub módulos \"Comprobantes de Compra\", \"Cambios Físicos\" y \"Devoluciones\" y se deben de ocultar los sub módulos \"Reportes\" y \"Gráficas\"\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_044_ValidarVisibilidadSubmodulosPerfilComercialEnPortalCometa_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}