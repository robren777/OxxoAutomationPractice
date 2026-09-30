package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que los elementos interactivos estan ubicados correctamente y las funcionalidades existentes se mantienen"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_050_Validar elementos de comprobante de compra en directos
*
*PREREQUISITOS:
*1. Credenciales para ingresar al Portal cometa,  validar el formato con la liga ux
*2.Liga de Portal Cometa
*
*INSUMOS: 
 *Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
 *Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
 *
*
* @author Alejandro Valdez
* @date Julio 2025
*/
public class ATC_FT_050_ValidarComprobanteCompraDirectos extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_050_ValidarComprobanteCompraDirectos_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		/*
		 * Paso 1 - Ingresar a la liga del portal con las con credenciales 
		 */
		addStep("Ingresar a la liga del portal con las con credenciales"); 
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
		 * Paso 4 - Seleccionar directos
		 */
		addStep("Seleccionar directos");
		validacion = pc.opcDirectosPortalCometa(false);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.directosPortalCometaSc();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta"); 
		
		/*
		 * Paso 5 - Seleccionar comprobante de compras
		 */
		addStep("Seleccionar comprobante de compras");
		validacion = pc.opcComprobantesCompra(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		
		/*
		 * Paso 6 -Se visualizan los campos nombre del proveedor, fuente de abasto, zona region, plaza, tienda, rango de fecha.
		 */
		addStep("Se visualizan los campos nombre del proveedor, fuente de abasto, zona region, plaza, tienda, rango de fecha.");
		validacion = pc.comprobantesCompraCampos();
		u.hardWait(15);
		assertTrue(validacion,"No se muestra la pantalla de manera correcta"); 
		
		/*
		 * Paso 7 - Validar que se puede interactuar con los campos correctamente.
		 */
		addStep("Validar que se puede interactuar con los campos correctamente.");
		validacion = pc.selectProveedor();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.selectFa(false);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.selectZona();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.selectRegion();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.selectCrPlaza(); 
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.selectCrTienda();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.sltFechase(5);
		assertTrue(validacion,"No se pudieron seleccionar las fechas de manera correcta.");
		
		/*
		 * Paso 8 - Validar que se visualicen botones limpiar filtro y consultar
		 */
		addStep("Validar que se visualicen botones limpiar filtro y consultar");
		validacion = pc.opcConsultarLimpiarFiltrosDisp();
		assertTrue(validacion	,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 9 - Validar boton limpiar filtro
		 */
		addStep("Validar boton limpiar filtro");
		validacion = pc.opcLimpiarFiltrosCC();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.validarFiltrosClean();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 10 - Validar boton consultar
		 */
		addStep("Validar boton consultar");
		pc.busquedaFiltroPlazaFechaDirectos(user,false);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.regComprobanteCompra();
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
		return "1. Credenciales para ingresar al Portal cometa,  validar el formato con la liga ux"
				+ "2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que los elementos interactivos estan ubicados correctamente y las funcionalidades existentes se mantienen\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_050_ValidarComprobanteCompraDirectos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}