package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: Validar cuando se haya seleccionado un Proveedor con el fuente de abasto ligado a 
 * este proveedor, me devuelva todos los registros ligados a lo que se filtro anteriormente mencionado. 
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_477_Validar que permita seleccionar un Proveedor con el Fuente de Abasto anidado y me 
* devuelva todos los registros ligados a estos datos - "Cambios Físicos Pendientes" Directos
*
*PREREQUISITOS:
1. Credenciales para el perfil DIRECTOS con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial], validar desde el criterio   
2.Liga de Portal Cometa
*
*INSUMOS: 
*Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
*URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Alejandro Valdez
* @date Febrero 2026
*/
public class ATC_FT_477_SeleccionProveedorFuenteAbastoAnidadoPendientes extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_477_SeleccionProveedorFuenteAbastoAnidadoPendientes_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
        boolean validacion = pc.obtenerTndyProveedorCambiosFisicosPendientes(true, user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla");
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
		 * Paso 6 - Seleccionar el botón "Cambios físicos pendientes actuales"
		 */
		addStep("Seleccionar el botón \"Cambios físicos pendientes actuales\"");
		validacion = pc.btnCambiosFisicosPendientesActuales(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta"); 
		
		/*
		 * Paso 7 y 8 - Dar clic el campo Proveedores + Selecciona un Proveedor
		 */
		addStep("Dar clic el campo Proveedores + Selecciona un Proveedor");
		validacion = pc.selectProveedorBd();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta"); 
		
		/*
		 * Paso 9 y 10 - Dar clic el campo Fuente de Abasto + Selecciona una Fuente de Abastos
		 */
		addStep("Dar clic el campo Fuente de Abasto + Selecciona una Fuente de Abastos");
		validacion = pc.selectFuenteAbasto();
        assertTrue(validacion, "No se pudo validar el filtro vs el grid");
        
        /*
		 * Paso 9 - Dar clic en el botón Consultar
		 */
		addStep("Dar clic en el botón Consultar");
		validacion = pc.opcConsultar(true);
		assertTrue(validacion,"No se permite seleccionar correctamente");
		validacion = pc.validarSkusGridConBD(true);
		assertTrue(validacion,"No se realizo la validación correctamente");



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
		return "1. Credenciales para el perfil DIRECTOS con usuario  [Admin, OXXO OS, OXXO Plaza, Administrativo Operativo, Comercial], validar desde el criterio   "
				+ "2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar cuando se haya seleccionado un Proveedor con el fuente de abasto ligado a este proveedor, "
				+ "me devuelva todos los registros ligados a lo que se filtro anteriormente mencionado.";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_477_SeleccionProveedorFuenteAbastoAnidadoPendientes_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}