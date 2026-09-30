package WEB.cometa.scripts.mvp4;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometaMVP4;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**DESCRIPCIÓN: "Validar que en centro de descarga, en el modulo de notifiaciones, se registren las peticiones desde que el usuario realiza de descarga"
*
*NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* ATC_FT_402_EstatusProcesoDescargas
*
*PREREQUISITOS:
*1. Credenciales con usuario [Admin, Administrativo Operativo, Comercial] validar desde el criterio 
*2. Liga de Portal Cometa 
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
public class ATC_FT_402_EstatusProcesoDescargas extends BaseExecution { 
	SeleniumUtil u;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_402_EstatusProcesoDescargas_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Portal Web: Cometa");
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("user");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase); // crea objeto de locator
		
		addStep("Precondicion");
		boolean validacion = pc.validarYObtenerFecha(user);
        assertTrue(validacion,"No se obtuvieron datos en la tabla xxcm_historico_directos_view");
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
		 * Paso 5 - Ingresar a Comprobantes de Compra
		 */
		addStep("Ingresar a Comprobantes de Compra");
		validacion = pc.opcComprobantesCompra(false);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta"); 
		validacion = pc.comprobantesCompraCampos();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 6 - Seleccionar rango de fechas
		 */
		addStep("Seleccionar rango de fechas");
		u.hardWait(5);
		validacion = pc.fechasBd(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		
		/*
		 * Paso 7 - Dar clic en el Botón Consultar
		 */
		addStep("Dar clic en el Botón Consultar");
		validacion = pc.opcConsultar(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		
		/*
		 * Paso 8 - Dar clic en botón Descargar resumen de todos los comprobantes filtrados (registrar la fecha y hora en que se realizó la descarga)
		 */
		addStep("Dar clic en botón Descargar resumen de todos los comprobantes filtrados (registrar la fecha y hora en que se realizó la descarga)");
		validacion = pc.clicExcelResumen(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.registrarFechaHora();
		assertTrue(validacion,"No se pudo realizar el registro de los datos de manera correcta");
		u.hardWait(1);
		pc.opcCerrarX(false);
		
		/*
		 * Paso 9 - Dar click en botón Inicio Externo
		 */
		addStep("Dar click en botón Inicio Externo");
		validacion = pc.opcInicioExterno(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		
		/*
		 * Paso 10 - Se da clic en menú centro de descarga
		 */
		addStep("Se da clic en menú centro de descarga");
		validacion = pc.opcCentroDescargas(true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		validacion = pc.scCentroDeDescargas();
		assertTrue(validacion,"No se muestra la pantalla de manera correcta");
		
		/*
		 * Paso 11 - Seleccionar rango de fechas
		 */
		addStep("Seleccionar rango de fechas");
		u.hardWait(2);
		validacion = pc.FiltroRangoFechasCentroDescarga(1,true);
		assertTrue(validacion,"No se pudo seleccionar la opción de manera correcta");
		pc.opcConsultar(false);
		
		/*
		 * Paso 12 - Se valida que se registró la última descarga realizada 
		 */
		addStep("Se valida que se registró la última descarga realizada");
		validacion = pc.valRegCentroDeDescargas();
		assertTrue(validacion,"No se muestran los datos de manera correcta");
		
		/*
		 * Finalizar el proceso
		 */

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
		return "1. Credenciales con usuario [Admin, Administrativo Operativo, Comercial] validar desde el criterio \n"
				+ "2. Liga de Portal Cometa ";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que en centro de descarga, en el modulo de notifiaciones, se registren las peticiones desde que el usuario realiza de descarga";
	}

	@Override
	public String setTestDesigner() {
		return"Alejandro Valdez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_402_EstatusProcesoDescargas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}