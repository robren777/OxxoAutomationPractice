package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar  que al utilizar el usuario admin  y la búsqueda que realice el portal CEDIS no encuentra información debe de mostrarse el siguiente mensaje
*"Esta devolución no se realizo por medio del proceso de verificación digital"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* MTC_FT_029_Validar que al no encontrar informacion al realizar busqueda se muestre mensaje con usuario CEDIS
* 
*PREREQUISITOS:
*1. Credenciales para el perfil CEDIS, validar desde el criterio y la liga ux     
*2.Liga de Portal Cometa
*
*INSUMOS: 
*Contar con información disponible en tabla xxcm_historico_cedis_view.
*Credenciales creadas por team kyndril en soporte L2 con doble autenticación desactivada
*URL: jdbc:postgresql://oxzaspocmdbq01.postgres.database.azure.com:5432/cometa
*Liga externa de red libre https://pruebasweb.oxxo.com/AuthFemcoProx/
*
* @author Alejandro Valdez
* @date Julio 2025
*/
public class ATC_FT_029_MensajeSinResultadosBusquedaCEDIS extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_029_MensajeSinResultadosBusquedaCEDIS_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator

		addStep("Precondicion");
		boolean ResultPre = pc.ValidaBDExistanDatosFiltrosGridCedis(true, user);
        assertTrue(ResultPre,"No se obtuvieron datos en la tabla xxcm_historico_cedis_view");
        String password = pc.obtenerPasswordPorUsuario(user);
        assertTrue(password != null, "No se encontro password configurada para el usuario: " + user);
		
		/*
		 * Paso 1 - Ingresar a la liga del portal con usuario CEDIS
		 */
		addStep("Ingresar a la liga del portal con usuario CEDIS"); 
		u.get(data.get("Url")); 
		u.refresh();
		u.hardWait(1);
		boolean validacion = pc.loginScreen();
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
		validacion = pc.portalCometaScCedisSE();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 4 - Ingresar a CEDIS
		 */
		addStep("Ingresar a CEDIS");
		validacion = pc.opcCedisPortalCometa();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.cedisPortalCometaScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 5 - Posicionarse al icono CEDIS se despliega una lista o hacer clic sobre el menú cedis
		 */
		addStep("Posicionarse al icono CEDIS se despliega una lista o hacer clic sobre el menú cedis");
		validacion = pc.selecListaCedis();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		u.hardWait(2);
		validacion = pc.opcListaCedis();
		assertTrue(validacion,"No se muestra la lista de manera correcta");
		
		/*
		 * Paso 6 - Seleccionar Recepción de Transferencias y Devoluciones
		 */
		addStep("Seleccionar Recepción de Transferencias y Devoluciones");
		validacion = pc.opcRTyDevoluciones();
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.RTyDevolucionesScreen();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 7 - Realizar una busqueda con la cual no se encuentre información
		 * Se muestra el siguiente mensaje "Esta devolución no se realizo por medio del proceso de verificación digital"
		 */
		addStep("Realizar una busqueda con la cual no se encuentre información"
				+ "Se muestra el siguiente mensaje \"Esta devolución no se realizo por medio del proceso de verificación digital\"");
		validacion = pc.busquedaFiltroPlazaFechaCedisSinVerif(user,true);
        assertTrue(validacion, "No se regresaron datos del filtro");
		pc.scFormatoVerificacion();
		pc.ckFirstFormVerificacion(false);
		validacion = pc.msgDevNoSeRealizoPorVD();
		assertTrue(validacion,"No se muestra el mensaje de manera correcta");
		

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
		return "1. Credenciales para el perfil CEDIS, validar desde el criterio y la liga ux     2.Liga de Portal Cometa";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar  que al utilizar el usuario admin  y la búsqueda que realice el portal CEDIS no encuentra información debe de mostrarse el siguiente mensaje\r\n"
				+ " \"Esta devolución no se realizo por medio del proceso de verificación digital\"\"\r\n";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_029_MensajeSinResultadosBusquedaCEDIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
